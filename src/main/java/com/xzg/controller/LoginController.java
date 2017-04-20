/**
 * 
 */
package com.xzg.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xzg.dao.UserResportDao;
import com.xzg.domain.User;
import com.xzg.exption.Assert;
import com.xzg.exption.BusinessException;
import com.xzg.exption.ErrorKind;
import com.xzg.mapper.UserMapper;
import com.xzg.serviceImple.UpdateServiceImp;
import java.util.List;
import java.util.UUID;

//@EnableAutoConfiguration
//*Unable to start EmbeddedWebApplicationContext due to missing EmbeddedServletContainerFactory bean.
//@SpringBootApplication
//@RestController
@Controller
public class LoginController {
	 private static Logger logger = Logger.getLogger(LoginController.class);
	@Autowired
	private UserResportDao userResportDao;
	@Autowired
	private UpdateServiceImp updateServiceImp;
	@Autowired
	private UserMapper usermapper;
	@RequestMapping("/")
    public String index() {
        return "login";
    }
    @RequestMapping("/getUser.do")
    @ResponseBody
    public List<User> getUser() {
    	List<User> list = new ArrayList<User>();
        list = userResportDao.getUserList();
        //System.out.println(list.size());
        return list;
    }
    /**
     * sessionId和uuid，sessionid是web服务器生成，uuid则是通过机器、时间、等生成的唯一编号，可用于替代sessionid
     * */
    @RequestMapping("/uid.do")
    @ResponseBody
    public String uid(HttpServletRequest request) {  
        HttpSession session = request.getSession();  
        UUID uid = (UUID) session.getAttribute("uid");
        if (uid == null) {
            uid = UUID.randomUUID();
        }
        session.setAttribute("uid", uid);
        logger.info("session.getAttribute(uid)："+session.getAttribute("uid")+"session.getId():"+session.getId());
        return  session.getId();  
    }
/** 
 * 将实现两个操作，一个是往Session中写入数据，另一个是查询数据，如下所示：
 * 然后查看spring是如何将自动替换和删除session
*/    @RequestMapping("/set.do")
	@ResponseBody
   public  String set(HttpServletRequest req) {
        req.getSession().setAttribute("testKey", "testValue");
        return "设置session:testKey=testValue";
    }

    @RequestMapping("/query.do")
    @ResponseBody
   public String query(HttpServletRequest req) {
        Object value = req.getSession().getAttribute("testKey");
        return "查询Session：\"testKey\"=" + value;
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
        user.setUserName("小单");
        user.setPassword("49BA59ABBE56E057");
        user.setEmail("123@qq.com");
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
   public User getMuser(@RequestParam int id){
	   User user = usermapper.getOne(id);
	   return user;
   }
  
   //websocket
   /*@RequestMapping(value="/webSocket.do",method={RequestMethod.GET,RequestMethod.POST})
   //@RequestParam("email")String email,@RequestParam("password")String password
   public String webSocket(){
	   UUID uid = (UUID) session.getAttribute("uid");
       if (uid == null) {
           uid = UUID.randomUUID();
       }
       session.setAttribute("uid", uid);
	   return "webSocket/webSocket";
   }*/
   //业务共有异常类测试
   @RequestMapping(value="/errorhtml.do",method=RequestMethod.GET)
   public ModelAndView exceptionForPageJumps(HttpServletRequest request){
       throw new BusinessException(ErrorKind.NULL_OBJ);
   }
   @RequestMapping(value="/businessException.do", method=RequestMethod.GET)
   @ResponseBody 
   public String businessException(HttpServletRequest request) {
	   
       throw new BusinessException(ErrorKind.ERROR_ADD_USER);
   }
   @RequestMapping(value="/otherException.do", method=RequestMethod.GET)
   @ResponseBody 
   public String otherException(HttpServletRequest request) throws Exception {
       throw new Exception();
   }
   //测试其他类中抛出异常是否捕获
   @RequestMapping(value="/testException.do",method=RequestMethod.GET)
   public void sysexception(){
		// TODO Auto-generated catch block
	   Assert.asse(1==3, "1不等于3");
   }
   
}