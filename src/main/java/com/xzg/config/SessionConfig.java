package com.xzg.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
/**
 * 分布式系统中，sessiong共享有很多的解决方案，其中托管到缓存中应该是最常用的方案之一，
 * Session配置:maxInactiveIntervalInSeconds: 设置Session失效时间，使用Redis Session之后，
 * 原Boot的server.session.timeout属性不再生效
 * */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 86400*10)
public class SessionConfig {
}