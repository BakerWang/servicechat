package com.xzg.service;

import com.xzg.domain.User;

public interface ServiceLogin {
	public boolean checkPassword(int id,String password);
	public User chkPwdRt(int id,String password);
	public void updateUserIpById(User user);
}
