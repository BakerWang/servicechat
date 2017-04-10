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
	
	private ValueOperations<String, User> operations=redisTemplate.opsForValue();
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
		userResportDao.save(user);
		//保存到redis
       operations.set("t_user"+user.getId(), user,10,TimeUnit.SECONDS);
	}
	@SuppressWarnings("unchecked")
	@Override
	public User findByUserId(int id){
		User user = null;
		 boolean exists=redisTemplate.hasKey("user"+id);
		 if(exists){
			 	user  = operations.get("user");
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
