package com.xzg.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import com.xzg.hander.CountWebSocketHandler;
import com.xzg.hander.HandshakeInterceptor;

@Configuration
@EnableWebSocket//开启websocket  
//@EnableWebSocketMessageBroker
//通过EnableWebSocketMessageBroker 开启使用STOMP协议来传输基于代理(message broker)的消息,
//此时浏览器支持使用@MessageMapping 就像支持@RequestMapping一样。
/**首先要注入ServerEndpointExporter，这个bean会自动注册使用了@ServerEndpoint注解声明的Websocket
endpoint。要注意，如果使用独立的servlet容器，而不是直接使用springboot的内置容器，就不要注入ServerEndpointExporter，
因为它将由容器自己提供和管理。*/
public class WebSocketConfig implements WebSocketConfigurer {
    @Bean
    public ServerEndpointExporter serverEndpointExporter(ApplicationContext context) {
        return new ServerEndpointExporter();
    }
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new CountWebSocketHandler(), "/ws").addInterceptors(new HandshakeInterceptor());
        
       // registry.addHandler(echoWebSocketHandler(), "/echo");
        registry.addHandler(new CountWebSocketHandler(), "/echo").withSockJS();
    }
    /*@Bean
    public WebSocketHandler echoWebSocketHandler() {
    	 return new EchoWebSocketHandler();
    }*/
}