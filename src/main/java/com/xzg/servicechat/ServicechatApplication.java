package com.xzg.servicechat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.xzg.controller.HelloWorldController;

@EnableAutoConfiguration
//*Unable to start EmbeddedWebApplicationContext due to missing EmbeddedServletContainerFactory bean.
@SpringBootApplication
@EnableJpaRepositories("com.xzg.dao")//扫描jpa dao的包
@EntityScan("com.xzg.domain")//扫描对应的实体类
@ComponentScan("com.xzg.serviceImple")//表示将该类自动发现（扫描）并注册为Bean，可以自动收集所有的Spring组件，包括@Configuration类。我们经常使用@ComponentScan注解搜索beans，并结合@Autowired注解导入。
public class ServicechatApplication {
	public static void main(String[] args) {
		//SpringApplication.run(ServicechatApplication.class, args);
		 SpringApplication.run(new Object[] { ServicechatApplication.class, HelloWorldController.class }, args);
	}
}