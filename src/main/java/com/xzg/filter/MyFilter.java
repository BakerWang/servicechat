package com.xzg.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

public class MyFilter implements Filter {
    @Override
    public void destroy() {
        // TODO Auto-generated method stub
    }
    @Override
    public void doFilter(ServletRequest srequest, ServletResponse sresponse, FilterChain filterChain)
            throws IOException, ServletException {
        // TODO Auto-generated method stub
        HttpServletRequest request = (HttpServletRequest) srequest;
        System.out.println("this is MyFilter,url :"+request.getRequestURI());
        filterChain.doFilter(srequest, sresponse);
    }
    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub
    }
    
    /**spring boot 采用自己SpringBoot 配置bean的方式进行配置的，SpringBoot提供了三种BeanFilterRegistrationBean、ServletRegistrationBean、ServletListenerRegistrationBean
    分别对应配置原生的Filter、Servlet、Listener,下面提供的三个配置和方案一采用的方式能够达到统一的效果*/
    /*@Bean
    public ServletRegistrationBean indexServletRegistration() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new MyFilter());
        registration.addUrlMappings("/hello");
        return registration;
    }

    @Bean
    public FilterRegistrationBean indexFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean(new MyFilter());
        registration.addUrlPatterns("/");
        return registration;
    }
    @Bean
    public ServletListenerRegistrationBean servletListenerRegistrationBean(){
        ServletListenerRegistrationBean servletListenerRegistrationBean = new ServletListenerRegistrationBean();
        servletListenerRegistrationBean.setListener(new MyFilter());
        return servletListenerRegistrationBean;
    }
    */
}
