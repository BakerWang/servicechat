package com.xzg.service;

import com.xzg.domain.FriendsInfo;
import com.xzg.domain.User;

public interface UpdateService {
	public String updateUser(String userName,int id);
	public void save(User user);
	public User findByUserId(int id);
	//操作friendsInfo
	public FriendsInfo getFriendById(int id);
	public void insertFrined(FriendsInfo friendsInfo);
	public void updateFriendsById(FriendsInfo friendsInfo);
}
