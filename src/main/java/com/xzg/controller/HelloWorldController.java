/**
 * 
 */
package com.xzg.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xzg.dao.UserResportDao;
import com.xzg.domain.User;
import com.xzg.serviceImple.UpdateServiceImp;

import java.util.List;
import java.util.UUID;



//@EnableAutoConfiguration
//*Unable to start EmbeddedWebApplicationContext due to missing EmbeddedServletContainerFactory bean.
//@SpringBootApplication
@RestController
public class HelloWorldController {
	@Autowired
	private UserResportDao userResportDao;
	@Autowired
	private UpdateServiceImp UpdateServiceImp;
	
    @RequestMapping("/getUser.do")
    @ResponseBody
    public List<User> getUser() {
    	List<User> list = new ArrayList<User>();
        User user=new User();
        user.setAge(2);
        user.setUserName("caiji");
        user.setPassword("xxxx");
        //userResportDao.save(user);
        list = userResportDao.getUserList();
        //System.out.println(list.size());
        return list;
    }
    @RequestMapping("/uid")
    @ResponseBody
    String uid(HttpSession session) {
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
    	String result = UpdateServiceImp.updateUser(name, 1);
    	return result;
    }
    
}