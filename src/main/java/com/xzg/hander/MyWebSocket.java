package com.xzg.hander;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
/**使用springboot的唯一区别是要@Component声明下，而使用独立容器是由容器自己管理websocket的，
但在springboot中连容器都是spring管理的。虽然@Component默认是单例模式的，但springboot还是会为
每个websocket连接初始化一个bean，所以可以用一个静态set保存起来。*/
@ServerEndpoint(value = "/ws")
@Component
public class MyWebSocket {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    private static Logger logger = Logger.getLogger(MyWebSocket.class);
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private volatile CopyOnWriteArraySet<MyWebSocket> webSocketSet = new CopyOnWriteArraySet<MyWebSocket>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        logger.info("有新连接加入！当前在线人数为" + getOnlineCount());
        try {
            //sendInfo("有新连接加入！当前在线人数为" + getOnlineCount());
            sendMessage("有新连接加入！当前在线人数为" + getOnlineCount());
        } catch (IOException e) {
            logger.error("socket初始异常");
            e.printStackTrace();
        }
    }
    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        logger.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }
    /**
     * 发生错误时调用
     * */
    @OnError
    public void onError(Session session, Throwable error) {
        logger.error("请求："+session.getRequestURI()+"发生错误");
        error.printStackTrace();
    }
    public void sendMessage(String message) throws IOException {
    	//getAsyncRemote()和getBasicRemote()确实是异步与同步的区别
        //this.session.getBasicRemote().sendText(message);
    	
        this.session.getAsyncRemote().sendText(message);
    }
    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * */
    @OnMessage
    public void sendInfo(String message) throws IOException {
        for (MyWebSocket item : webSocketSet) {
            try {
            	logger.info("客户端发来信息："+message);
                item.sendMessage(message);
            } catch (IOException e) {
                continue;
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