package com.xzg.service;

import com.xzg.domain.User;

public interface UpdateService {
	public String updateUser(String userName,int id);
	public void save(User user);
	public User findByUserId(int id);
}
