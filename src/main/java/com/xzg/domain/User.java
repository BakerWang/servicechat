/**
 * 
 */
package com.xzg.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "t_user")
public class User  implements Serializable{
	/**
	 * 
	 */
	/*public User(int id,String name,int age,String password){
		this.id=id;
		this.age=age;
		this.password=password;
		this.name=name;
		
	}
	public User(String name,int id){
		this.id=id;
		this.name=name;
		
	}
	public User(int id,String name,String password){
		this.id=id;
		this.name=name;
		this.password=password;
		
	}*/
	private static final long serialVersionUID = 5963616633321035548L;
	@Id
	@GeneratedValue
	private int id;
	  @Column(nullable = false, unique = false)//表示唯一
	private String userName;
	  @Column(nullable = false)
	private int age;
	  @Column(nullable = false)
	private String password;
	  @Column(nullable = false)
	 private String ip;
	  @Column(nullable = false)
	 private String email;
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
}
