package com.xzg.servicechat;

import javax.annotation.Resource;

import org.apache.catalina.core.ApplicationContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringRunner;

import com.xzg.dao.UserResportDao;
import com.xzg.domain.User;
@Configuration
@EnableJpaRepositories
@RunWith(SpringRunner.class)
@SpringBootTest
public class ServicechatApplicationTests {
	
	 @Resource
	    private UserResportDao userResportDao;
	 
	@Test
	public void contextLoads() {
		User user = new User();
		user.setAge(10);
		user.setId(1);
		user.setUserName("kfa");
		userResportDao.save(user);
		System.out.println(userResportDao.findAll().size());
	}

}
