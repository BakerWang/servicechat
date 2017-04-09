package com.xzg.serviceImple;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.xzg.dao.UserResportDao;
import com.xzg.service.PublicInfo;
import com.xzg.service.UpdateService;


@Service
public class UpdateServiceImp implements UpdateService,PublicInfo {
	@Resource
	private UserResportDao userResportDao;

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
}
