package com.xzg.serviceImple;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xzg.service.ServiceLogin;
import com.xzg.hander.Util.CookieUtil;
import com.xzg.mapper.UserMapper;
@Service
public class ServiceLoginImp implements ServiceLogin  {
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


}
