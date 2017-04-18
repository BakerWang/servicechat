package com.xzg.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
/*注解 EnableRedisHttpSession 创建了一个名为springSessionRepositoryFilter的Spring Bean，
该Bean实现了Filter接口。该filter负责通过 Spring Session
替换HttpSession从哪里返回。这里Spring Session是通过 redis 返回。 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 10*60, redisNamespace = "sessionSpace")  
/**
 * 分布式系统中，sessiong共享有很多的解决方案，其中托管到缓存中应该是最常用的方案之一，
 * Session配置:maxInactiveIntervalInSeconds: 设置Session失效时间，使用Redis Session之后，
 * 原Boot的server.session.timeout属性不再生效
 * */
public class SessionConfig {
/*	 httpSessionStrategy()，用来定义Spring Session的 HttpSession 集成使用HTTP的头来取代使用 
	 cookie 传送当前session信息。*/
	/**Spring Session对HTTP的支持是通过标准的servlet filter来实现的，这个filter必须要配置
	为拦截所有的web应用请求，并且它应该是filter链中的第一个filter。Spring Session 
	filter会确保随后调用javax.servlet.http.HttpServletRequest的getSession()方法时，
	都会返回Spring Session的HttpSession实例，而不是应用服务器默认的HttpSession。*/
	/*@Bean  
    public HttpSessionStrategy httpSessionStrategy() {  
        return new HeaderHttpSessionStrategy();  
    } */
	//如果使用下面的代码，则是使用cookie来传送 session 信息。
	/*@Bean  
	public HttpSessionStrategy httpSessionStrategy() {  
	    return new CookieHttpSessionStrategy();  
	} */
}