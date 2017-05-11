/**
 * 
 */
package com.xzg.domain;

import java.io.Serializable;
import java.util.List;


public class User2  implements Serializable{
	private static final long serialVersionUID = 5963616633321035548L;
	private int id;
	private String userName;
	private int age;
	private String password;
	 private String ip;
	 private String email;
	private List<FriendsInfo> friends;
	public List<FriendsInfo> getFriends() {
		return friends;
	}
	public void setFriends(List<FriendsInfo> friends) {
		this.friends = friends;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.id+":"+this.email;
	}
}
