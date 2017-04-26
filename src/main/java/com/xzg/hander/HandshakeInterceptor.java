package com.xzg.hander;
import org.apache.log4j.Logger;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;
/**
 * 消息拦截处理类
 * 可以在客户端和服务端进行websocket握手时，获取到中间信息，并将其保存到websocket session的attribute中。 
 */
public class HandshakeInterceptor extends HttpSessionHandshakeInterceptor {
	 private static Logger logger = Logger.getLogger(HandshakeInterceptor.class);
	 /**
	     * 进入hander之前的拦截
	     */
	 @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
    		WebSocketHandler wsHandler,Map<String, Object> attributes) throws Exception {
        //解决The extension [x-webkit-deflate-frame] is not supported问题
        if (request.getHeaders().containsKey("Sec-WebSocket-Extensions")) {
            request.getHeaders().set("Sec-WebSocket-Extensions", "permessage-deflate");
        }
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
           // HttpSession session = servletRequest.getServletRequest().getSession(false);
            String userId = servletRequest.getServletRequest().getParameter("userId");
            if (userId != null) {
                //使用userName区分WebSocketHandler，以便定向发送消息
//                String userName = (String) session.getAttribute("WEBSOCKET_USERNAME");
            	attributes.put("WEBSOCKET_USERNAME",userId);
            }
        }
        logger.info("Before Handshake");
        /*return super.beforeHandshake(request, response, wsHandler, attributes);*/
        return true;
    }
	 /** 
	     * //进入hander之后的拦截
	     */
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                               Exception ex) {
         logger.info("After Handshake");  
        super.afterHandshake(request, response, wsHandler, ex);
    }
}