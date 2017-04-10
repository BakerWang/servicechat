package com.xzg.servicechat;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xzg.domain.User;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestRedis {
	//直接保存为string类型
	/*@Autowired
    private StringRedisTemplate stringRedisTemplate;*/
	@Autowired
	private RedisTemplate redisTemplate; 
    @Test
    public void test() throws Exception {
        // 保存字符串
       /* stringRedisTemplate.opsForValue().set("aaa", "111");
        Assert.assertEquals("111", stringRedisTemplate.opsForValue().get("aaa"));*/
    	 User user=new User();
    	 user.setId(1);
    	 user.setPassword("0000");
         ValueOperations<String, User> operations=redisTemplate.opsForValue();
         operations.set("user", user);
         //operations.set("com.xzg.user", user,1,TimeUnit.SECONDS);
         Thread.sleep(1000);
         //redisTemplate.delete("com.xzg.user");
         boolean exists=redisTemplate.hasKey("user");
         if(exists){
             System.out.println("===============exists is true"+operations.get("user").getPassword());
         }else{
             System.out.println("===================exists is false");
         }
        // Assert.assertEquals("aa", operations.get("com.neo.f").getUserName());
    }
    
}