package com.xzg.serviceImple;


import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.xzg.service.ServiceLogin;
import com.xzg.domain.User;
import com.xzg.hander.Util.CookieUtil;
import com.xzg.mapper.UserMapper;
@Service
public class ServiceLoginImp implements ServiceLogin  {
	 private static Logger logger = Logger.getLogger(ServiceLoginImp.class);
	 @Resource
	private UserMapper usermapper;
	//保存到redis
	@Override
	public boolean checkPassword(int id,String password) {
		// TODO Auto-generated method stub
		 password=CookieUtil.getMD5(password);//加密用户密码
		 long result = 0L;
		 result = usermapper.isExits(id, password);
		 if(result  == 0L){
			 return false;
		 }
		return true;
	}
	@Override
	public User chkPwdRt(int id,String password) {
		User user =null;
		password=CookieUtil.getMD5(password);//加密用户密码
		logger.info("password:"+password);
		user = usermapper.returnUserByPw(id, password);
		logger.info("updateUser:"+user);
		return user;
	}
	@Override
	public void updateUserIpById(User user){
		try {
			usermapper.updateUser(user);
			logger.info("updateUser:"+user.getId());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
