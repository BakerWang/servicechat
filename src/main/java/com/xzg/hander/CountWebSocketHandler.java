package com.xzg.hander;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;

/**
 * @author xzg
 *handler定义了一系列websocket通信流程中的接口，包括连接建立（ afterConnectionEstablished ）、
 *消息接收（ handleTextMessage ）、连接关闭（ afterConnectionClosed ）等。可以通过试下这些方法来实现业务逻辑。
 */
@Component
public class CountWebSocketHandler extends TextWebSocketHandler {
    private static Logger logger = Logger.getLogger(CountWebSocketHandler.class);
    private static Map<String,WebSocketSession> sessionMap = new HashMap<String,WebSocketSession>();
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        session.sendMessage(new TextMessage(session.getPrincipal().getName()+",你是第" + (sessionMap.size()) + "位访客")); //p2p
        Collection<WebSocketSession> sessions = sessionMap.values();
        for (WebSocketSession ws : sessions) {//广播
            ws.sendMessage(message);
        }
        sendMessage(sessionMap.keySet(),"你好");
    }
    //初次链接成功执行
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    	logger.debug("链接成功......");
        sessionMap.put(session.getPrincipal().getName(),session);
        String userName = (String) session.getAttributes().get("WEBSOCKET_USERNAME");
        if(userName!= null){
            //查询未读消息
            int count = 5;
            session.sendMessage(new TextMessage(count + ""));
        }
        super.afterConnectionEstablished(session);
    }
    
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    	logger.info("princal========="+session.getPrincipal().getName());
        String princal = session.getPrincipal().getName();
        if(princal != null){
        	sessionMap.remove(princal);
       }
        super.afterConnectionClosed(session, status);
    }
    /**
     * 发送消息
     */
    public static void sendMessage(String username,String message) throws IOException {
        sendMessage(Arrays.asList(username),Arrays.asList(message));
    }
    /**
     * 发送消息
     */
    public static void sendMessage(Collection<String> acceptorList,String message) throws IOException {
        sendMessage(acceptorList,Arrays.asList(message));
    }
    /**
     * 发送消息，p2p 群发都支持
     */
    public static void sendMessage(Collection<String> acceptorList, Collection<String> msgList) throws IOException {
        if (acceptorList != null && msgList != null) {
            for (String acceptor : acceptorList) {
                WebSocketSession session = sessionMap.get(acceptor);
                if (session != null) {
                    for (String msg : msgList) {
                        session.sendMessage(new TextMessage(msg.getBytes()));
                    }
                }
            }
        }
    }
    /**
     * 给某个用户发送消息
     * @param userName
     * @param message
     */
    public void sendMessageToUser(String userName, TextMessage message) {
        for (WebSocketSession user : sessionMap.values()) {
            if (user.getAttributes().get("WEBSOCKET_USERNAME").equals(userName)) {
                try {
                    if (user.isOpen()) {
                        user.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}