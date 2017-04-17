package com.xzg.domain;

import java.io.Serializable;

public class FriendsInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int user_id;
	private int friends_id;
	private String friends_name;
	private char isonline;
	private UserSexEnum userSex;
	public UserSexEnum getUserSex() {
		return userSex;
	}
	public void setUserSex(UserSexEnum userSex) {
		this.userSex = userSex;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getFriends_id() {
		return friends_id;
	}
	public void setFriends_id(int friends_id) {
		this.friends_id = friends_id;
	}
	public String getFriends_name() {
		return friends_name;
	}
	public void setFriends_name(String friends_name) {
		this.friends_name = friends_name;
	}
	public char getIsonline() {
		return isonline;
	}
	public void setIsonline(char isonline) {
		this.isonline = isonline;
	}
}
