package com.xzg.hander;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import com.xzg.config.GetHttpSessionConfigurator;
import com.xzg.domain.User;
/**使用springboot的唯一区别是要@Component声明下，而使用独立容器是由容器自己管理websocket的，
  *但在springboot中连容器都是spring管理的。虽然@Component默认是单例模式的，但springboot还是会为
  *每个websocket连接初始化一个bean，所以可以用一个静态set保存起来。configurator添加这个配置
*/
@ServerEndpoint(value = "/ws",configurator = GetHttpSessionConfigurator.class)//保证session中是有值的，不然就无法建立websocket链接且报空指针错误
@Component
public class MyWebSocket {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    private static Logger logger = Logger.getLogger(MyWebSocket.class);
    /**这里定义存储集合：
     * 1、concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     * 2、使用static和final修饰而不是volatile，final static修饰可以表示全局变量他是它不会因为实例后的对象不同而导致的不同，所以可以
     * 存储每一个实例化后的this。
     * 3、volatile只能保证内存的可见性，并不能存储每一个实例后的对象
     */
    private final static CopyOnWriteArraySet<MyWebSocket> webSocketSet = new CopyOnWriteArraySet<MyWebSocket>();
    
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    private static final String GUEST_PREFIX = "Guest";  
    private final String nickname;
    private static HttpSession httpSession;
    public MyWebSocket() {  
        nickname = GUEST_PREFIX;  
    }
    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session,EndpointConfig config) {
        this.session = session;
        httpSession = (HttpSession) config.getUserProperties().get(  
                HttpSession.class.getName()); 
        
        webSocketSet.add(this);     //加入set中
        //获取session中对象
        User user = (User)httpSession.getAttribute("loginuser");
        logger.info("用户姓名"+user.getUserName());
        String message = String.format("* %s %s", nickname, "has joined.");
        addOnlineCount();           //在线数加1
        logger.info("有新连接加入！当前在线人数为" + getOnlineCount());
        //sendInfo("有新连接加入！当前在线人数为" + getOnlineCount());
		//sendMessage(message);
		broadcast(message);
    }
    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        logger.info("有一连接关闭！当前在线人数为" + getOnlineCount());
        String message = String  
                .format("* %s %s", nickname, "has disconnected.");  
        //sendMessage(message);
		broadcast(message);
    }
    /**
     * 发生错误时调用
     * */
    @OnError
    public void onError(Session session, Throwable error) {
        logger.error("请求："+session.getRequestURI()+"发生错误");
        error.printStackTrace();
    }
    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * */
    @OnMessage
    public void onMessage(String message) throws IOException {
    	//群发消息
    	broadcast(message);
    	//单独发给某一个用户
    	//sendUserByidInfo(message);
    }
    /**
     * 群发自定义消息
     * *//*
    public  void sendInfo(String message) throws IOException {
        for (MyWebSocket item : webSocketSet) {
            try {
            	 synchronized (item){
            		 item.sendMessage(message);
            	 }
            } catch (IOException e) {
            	System.out  
                .println("Chat Error: Failed to send message to client");  
            	webSocketSet.remove(item); 
                continue;
            }
        }
        
    }*/
    /**2017年4月25日
     * @author xzg
     * TODO 发消息给客户端
     */
    public void sendMessage(String message) throws IOException {
    	//getAsyncRemote()和getBasicRemote()确实是异步与同步的区别
        //this.session.getBasicRemote().sendText(message);
        this.session.getAsyncRemote().sendText(message);
    }
    /**2017年4月25日
     * @author xzg
     * TODO 广播
     */
    private static void broadcast(String msg) {  
        for (MyWebSocket item : webSocketSet) {  
            try {  
                synchronized (item) {  
                	item.session.getBasicRemote().sendText(msg);  
                }  
            } catch (IOException e) {  
                System.out  
                        .println("Chat Error: Failed to send message to client");  
                webSocketSet.remove(item);  
                try {  
                	item.session.close();  
                } catch (IOException e1) {  
                }  
                String message = String.format("* %s %s", item.nickname,  
                        "has been disconnected.");  
                broadcast(message);  
            }  
        }  
    } 
    private static void sendUserByidInfo(String msg){
    	for (MyWebSocket item : webSocketSet) {  
            try { 
            	//发给用户指定用户的,这里的值需要和所有开启websocket中的用户进行比较
            	String id = "1";
            	String userId = String.valueOf(item.httpSession.getAttribute("id"));
            	logger.info("发送用户的id："+userId);
            	if(id.equals(userId)){
            		synchronized (item) { 
            			item.session.getBasicRemote().sendText(msg);
                		}
            	} 
            }catch (IOException e) {  
                System.out  
                        .println("Chat Error: Failed to send message to client");  
                webSocketSet.remove(item);  
                try {  
                	item.session.close();  
                } catch (IOException e1) {  
                }  
                String message = String.format("* %s %s", item.nickname,  
                        "has been disconnected.");  
                broadcast(message);  
            }  
        }  
    }
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        MyWebSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        MyWebSocket.onlineCount--;
    }
}