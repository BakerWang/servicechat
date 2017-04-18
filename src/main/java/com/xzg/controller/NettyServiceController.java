package com.xzg.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.xzg.domain.FriendsInfo;
import com.xzg.domain.User;
import com.xzg.domain.UserSexEnum;
import com.xzg.exption.Assert;
import com.xzg.serviceImple.ServiceLoginImp;
import com.xzg.serviceImple.UpdateServiceImp;

@Controller
public class NettyServiceController {

	private static Logger logger = Logger.getLogger(NettyServiceController.class);
	@Autowired
	private UpdateServiceImp updateServiceImp;
	@Resource
	private ServiceLoginImp serviceLoginImp;
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
		logger.info("friendsInfo："+friendsInfo.getIsonline());
		return friendsInfo;
	   }
	
	@RequestMapping(value="/login.do",method={RequestMethod.GET,RequestMethod.POST})
	public String loginin(@RequestParam("email")String userid,@RequestParam("password")String password,
			HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes){
		String forword="";
		
		int id = Integer.valueOf(userid);
		if((userid!=null&&userid.length()>0)&&(password!=null&&password.length()>0)){
			boolean bl = serviceLoginImp.checkPassword(id, password);
			if(bl){
				User user= updateServiceImp.findByUserId(id);
				//request.getSession().setAttribute("loginuser", user);
				if(userid.equals("")){
					redirectAttributes.addFlashAttribute("message", "您已登录，请不要重复登录!");
					forword="/login.do";//login.jsp
				}else{
						/*application.setAttribute("userid", userid);
					 	request.getSession().setAttribute("sessionListener", sessionListener);*/
						redirectAttributes.addFlashAttribute("message", "登录成功!");
						forword="user/menue";//main.jsp
				}
			}else{
				redirectAttributes.addFlashAttribute("message", "用户名或密码错误!");
				//登录失败，在login_tmp表中更新字段num-1直到为0时锁定用户（5分钟内）当锁定用户时禁止登录
				forword="/login.do";//login.jsp
			}
		}else{
			forword="/login.do";//login.jsp
			redirectAttributes.addFlashAttribute("message", "用户名或密码不能为空!");
		}
		return "redirect:"+forword;
}
	
}
