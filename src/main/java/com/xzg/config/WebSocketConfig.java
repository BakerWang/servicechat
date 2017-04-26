/*package com.xzg.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.xzg.hander.CountWebSocketHandler;
import com.xzg.hander.HandshakeInterceptor;


@Configuration
@EnableWebSocket//开启websocket  
//@EnableWebSocketMessageBroker 开启使用STOMP协议来传输基于代理(message broker)的消息,
//此时浏览器支持使用@MessageMapping 就像支持@RequestMapping一样。
*//**第二种spring通用websocket方法
 * 首先要注入ServerEndpointExporter，这个bean会自动注册使用了@ServerEndpoint注解声明的Websocket
*endpoint。要注意，如果使用独立的servlet容器，而不是直接使用springboot的内置容器，
*就不要注入ServerEndpointExporter，因为它将由容器自己提供和管理。
*//*
public class WebSocketConfig implements WebSocketConfigurer {
	@Bean
    public CountWebSocketHandler serverEndpointExporter() {
        return new CountWebSocketHandler();
    }
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(serverEndpointExporter(), "/ws")
        .addInterceptors(new HandshakeInterceptor());//支持websocket 的访问链接
        registry.addHandler(serverEndpointExporter(),"/sockjs/echo")
        .addInterceptors(new HandshakeInterceptor()).withSockJS(); //不支持websocket的访问链接
    }
}*/