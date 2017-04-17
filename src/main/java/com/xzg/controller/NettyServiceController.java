package com.xzg.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xzg.domain.FriendsInfo;
import com.xzg.domain.UserSexEnum;
import com.xzg.exption.Assert;
import com.xzg.serviceImple.UpdateServiceImp;

@Controller
public class NettyServiceController {

	private static Logger logger = Logger.getLogger(NettyServiceController.class);
	@Autowired
	private UpdateServiceImp updateServiceImp;
	
	@RequestMapping(value="/insertFriend.do",method=RequestMethod.GET)
	   @ResponseBody
	   public void insertFriend(){
		FriendsInfo friendsInfo = new FriendsInfo();
		friendsInfo.setUser_id(1);
		friendsInfo.setFriends_id(2);
		friendsInfo.setUserSex(UserSexEnum.MAN);
		updateServiceImp.insertFrined(friendsInfo);
		   
	   }
	
	@RequestMapping(value="/findFriendById.do",method=RequestMethod.GET)
	   @ResponseBody
	   public FriendsInfo findFriendById(@RequestParam int id){
		FriendsInfo friendsInfo = updateServiceImp.getFriendById(id);
			Assert.asse(friendsInfo != null, "没有该朋友！");
		logger.info("friendsInfo"+friendsInfo.getFriends_id());
		return friendsInfo;
		   
	   }
	
}
