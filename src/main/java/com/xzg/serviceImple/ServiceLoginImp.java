package com.xzg.serviceImple;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import com.xzg.domain.User;
import com.xzg.service.ServiceLogin;

public class ServiceLoginImp implements ServiceLogin  {
	@Resource
	private RedisTemplate redisTemplate; 
	ValueOperations<String, User> operations=redisTemplate.opsForValue();
	//保存到redis
	@Override
	public boolean isTruePassword() {
		// TODO Auto-generated method stub
		
		return false;
	}


}
