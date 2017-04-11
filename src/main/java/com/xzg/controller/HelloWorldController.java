/**
 * 
 */
package com.xzg.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xzg.dao.UserResportDao;
import com.xzg.domain.User;
import com.xzg.mapper.UserMapper;
import com.xzg.serviceImple.UpdateServiceImp;

import java.util.List;
import java.util.UUID;

//@EnableAutoConfiguration
//*Unable to start EmbeddedWebApplicationContext due to missing EmbeddedServletContainerFactory bean.
//@SpringBootApplication
//@RestController
@Controller
public class HelloWorldController {
	@Autowired
	private UserResportDao userResportDao;
	@Autowired
	private UpdateServiceImp updateServiceImp;
	@Autowired
	private UserMapper usermapper;
	
    @RequestMapping("/getUser.do")
    @ResponseBody
    public List<User> getUser() {
    	List<User> list = new ArrayList<User>();
        list = userResportDao.getUserList();
        //System.out.println(list.size());
        return list;
    }
    @RequestMapping("/uid.do")
    @ResponseBody
    public String uid(HttpSession session) {
        UUID uid = (UUID) session.getAttribute("uid");
        if (uid == null) {
            uid = UUID.randomUUID();
        }
        session.setAttribute("uid", uid);
        return session.getId();
    }
    @RequestMapping("/update.do")
    @ResponseBody
    public String update(@RequestParam String name){
    	String result = updateServiceImp.updateUser(name, 1);
    	return result;
    }
    @RequestMapping(value="/findUserById.do",method = RequestMethod.GET)
    @ResponseBody
    public User findUserById(@RequestParam int id){
    	User user= null;
    	user = updateServiceImp.findByUserId(id);
    	return user;
    }
    @RequestMapping(value="/saveUser.do",method = RequestMethod.GET)
    @ResponseBody
    public void saveUser(){
    	User user=new User();
        user.setAge(5);
        user.setUserName("caiji");
        user.setPassword("8888");
    	updateServiceImp.save(user);
    }
    //尝试使用thymeleaf作为前端模板
    @RequestMapping(value="/hello.do",method=RequestMethod.GET)
    public String hello(Model model) {
        model.addAttribute("name", "Dear");
        return "hello";
    }
   //use mybits
   @RequestMapping(value="/getMuser.do",method=RequestMethod.GET)
   @ResponseBody
   public User getMuser(@RequestParam long id){
	   User user = usermapper.getOne(id);
	   return user;
   }
   @RequestMapping(value="/delMuser.do",method=RequestMethod.GET)
   public void delMuser(@RequestParam long id){
	   usermapper.delete(id);
   }
}