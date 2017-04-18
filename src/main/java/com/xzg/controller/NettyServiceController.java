package com.xzg.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
			HttpServletRequest request, HttpServletResponse response,Model model){
		String forword="";
		User user = null;
		int id = Integer.valueOf(userid);
		//boolean bl = serviceLoginImp.checkPassword(id, password);
		user = serviceLoginImp.chkPwdRt(id, password);
		if(null == user){
			 model.addAttribute("message", "用户名或密码错误!");
			forword="login";//
			//request.getSession().setAttribute("sessionListener", sessionListener);*/
			}else{
				request.getSession().setAttribute("loginuser", user);
				//登录失败，在login_tmp表中更新字段num-1直到为0时锁定用户（5分钟内）当锁定用户时禁止登录
				String ip = request.getLocalAddr();
				user.setIp(ip);
				serviceLoginImp.updateUserIpById(user);
				forword="user/menue";
			}
		return forword;
}
	@RequestMapping(value="/websocket.do",method={RequestMethod.GET,RequestMethod.POST})
	public String websocket(HttpSession httpSession,Model model){
		//进行相应的业务处理
		User user = null;
		user = (User)httpSession.getAttribute("loginuser");
		SimpleDateFormat sdf = new SimpleDateFormat("HH-mm-ss");
		String now = sdf.format(new Date());
		model.addAttribute("user", user);
		model.addAttribute("now", now);
		return "webSocket/webSocket";
	}
}
