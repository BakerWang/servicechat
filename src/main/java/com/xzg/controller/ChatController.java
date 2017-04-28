package com.xzg.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xzg.mapper.UserAutowordMapper;

@RequestMapping(value="/Chat")
public class ChatController implements ControllerLog{
	@Resource
	private UserAutowordMapper userAutowordMapper;
	
	@RequestMapping(value="/getFriendsByUser.do",method=RequestMethod.POST)
	@ResponseBody
	public Map<?,?> getFriendsByUser(){
		
		return null;
		
	}
	
}
