package com.xzg.filter;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.xzg.exption.Assert;
import com.xzg.service.PublicInfo;
@WebFilter(urlPatterns = "/*", filterName = "myfilter")
public class MyFilter implements Filter,PublicInfo {
    @Override
    public void destroy() {
        // TODO Auto-generated method stub
    }
    @Override
    public void doFilter(ServletRequest srequest, ServletResponse sresponse, FilterChain filterChain)
            throws IOException, ServletException {
        // TODO Auto-generated method stub
        HttpServletRequest request = (HttpServletRequest) srequest;
        String uri = request.getRequestURI();
        logger.info("this is MyFilter,url :"+uri);
        //正则表达式匹配
        String  regEx = ".do$";
        // 编译正则表达式
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(uri);
        // 字符串是否与正则表达式相匹配
        boolean rs = matcher.matches();
        HttpSession httpSession = request.getSession();
        if(rs){
        	  //防止篡改登录
             Assert.asse(httpSession.getAttribute("uid") != null, "请先登录！");
        }
        filterChain.doFilter(srequest, sresponse);
    }
    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub
    	logger.info("=========first init filter====");
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
