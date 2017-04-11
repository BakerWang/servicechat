package com.xzg.serviceImple;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.xzg.dao.UserResportDao;
import com.xzg.domain.User;
import com.xzg.service.PublicInfo;
import com.xzg.service.UpdateService;
@Service
public class UpdateServiceImp implements UpdateService,PublicInfo {
	@Resource
	private UserResportDao userResportDao;
	@SuppressWarnings("rawtypes")
	@Resource
	private RedisTemplate redisTemplate; 
	@Override
	@Transactional
	public String updateUser(String userName, int id) {
		// TODO Auto-generated method stub
		String result ="";
		try {
			userResportDao.updateUserById(userName, id);
			result = SUCCESS;
		} catch (Exception e) {
			// TODO: handle exception
			result = ERROR;
		}
		return result;
	}
	@Override
	@Transactional
	public void save(User user){
		ValueOperations<String, User> operations=redisTemplate.opsForValue();
		userResportDao.save(user);
		//保存到redis
       operations.set("t_user"+user.getId(), user,1000,TimeUnit.SECONDS);
       logger.info("=======save user user.getId()======"+user.getId());
	}
	@SuppressWarnings("unchecked")
	//@Cacheable(value="user-key")使用该注解可以自动在查询的时候优先查询redis缓存，value指的是redis的key值
	@Override
	public User findByUserId(int id){
		ValueOperations<String, User> operations=redisTemplate.opsForValue();
		User user = null;
		 boolean exists=redisTemplate.hasKey("t_user"+id);
		 logger.info("=========user id == "+id);
		 if(exists){
			 	user  = operations.get("t_user"+id);
			 	return user;
			}
		try {
			user = userResportDao.findByUserId(id);
		} catch (HibernateException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return user;
	}
}
