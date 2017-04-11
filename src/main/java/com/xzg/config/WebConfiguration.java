package com.xzg.config;

import org.apache.catalina.filters.RemoteIpFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;

import com.xzg.filter.MyFilter;
//Spring Boot自动添加了OrderedCharacterEncodingFilter和HiddenHttpMethodFilter，并且我们可以自定义Filter。


/**
 * @Configuration标注在类上,相当于把该类作为spring的xml配置文件中的<beans>，作用为：配置spring容器(应用上下文)
 * 实现Filter接口，实现Filter方法	
 * 添加@Configurationz 注解，将自定义Filter加入过滤链
*/

@Configuration
public class WebConfiguration {
	/*@Bean标注在方法上(返回某个实例的方法)，等价于spring的xml配置文件中的<bean>
	 * //@Bean注解注册bean,同时可以指定初始化和销毁方法
    //@Bean(name="testNean",initMethod="start",destroyMethod="cleanUp")
	 * */
    @Bean
    public RemoteIpFilter remoteIpFilter() {
        return new RemoteIpFilter();
    }
    @Bean
    public FilterRegistrationBean testFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new MyFilter());
        registration.addUrlPatterns("/*");
        //registration.addInitParameter("paramName", "paramValue");
        registration.setName("MyFilter");
        registration.setOrder(1);
        return registration;
    }
    //配置controller的对应的action映射路径
    @Bean
    public ServletRegistrationBean dispatcherRegistration(DispatcherServlet dispatcherServlet) {
        ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet);
        registration.addUrlMappings("*.do");
        registration.addUrlMappings("*.json");
        return registration;
    }

}