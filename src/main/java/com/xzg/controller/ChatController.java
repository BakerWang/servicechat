package com.xzg.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xzg.domain.FriendsInfo;
import com.xzg.domain.User;
import com.xzg.domain.User2;
import com.xzg.mapper.UserAutowordMapper;
import com.xzg.mapper.UserMapper;
@Controller
@RequestMapping(value="/Chat")
public class ChatController implements ControllerLog{
	@Resource
	private UserAutowordMapper userAutowordMapper;
	@Resource
	private UserMapper usermapper;
	
	@RequestMapping(value="/getFriendsByUser.do",method=RequestMethod.POST)
	@ResponseBody
	public Map<?,?> getFriendsByUser(){
		
		return null;
		
	}
	@RequestMapping(value="/FriendsByUser.do",method=RequestMethod.GET)
	@ResponseBody
	public List<User2> findbyid(@RequestParam int id){
		org.apache.ibatis.logging.LogFactory.useLog4JLogging();
		return usermapper.findFriendsByUser(id);
	}
}
