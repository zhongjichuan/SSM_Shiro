package com.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.demo.entity.User;
import com.demo.service.ShiroUserService;
import com.shiro.UserRealm;

@Controller
public class ShiroController {

	@Autowired
	ShiroUserService shiroUserService;
	
	@RequestMapping("/shiroController/login")
	public String login(HttpServletRequest request,String shiroLoginFailure){
		//未能成功
		String exception = (String) request.getAttribute("shiroLoginFailure");
		if(exception!=null)
			System.out.println(exception+"================");
		return "shiroLogin";
	}
	
	@RequestMapping("/shiroController/main")
	public String main(){
		return "shiro/main";
	}
	
	@RequestMapping("/shiroController/validateLogin")
	public String validate(User user,@RequestParam(value="rememberMe",required=false)Boolean rememberMe ,Model model,HttpServletRequest request) {
		String exception = (String) request.getAttribute("shiroLoginFailure");
		if(exception!=null)
			System.out.println(exception+"================");
		
		String errorMessage = null;
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(user.getName(),
				user.getPassword());
		if(rememberMe!=null&&rememberMe){
			System.out.println("记住我===============");
			token.setRememberMe(true);
		}
		try {
			//会在UserRealm中进行验证。。。
			subject.login(token);
		} catch (UnknownAccountException e) {
			errorMessage = "用户不存在";
		} catch (IncorrectCredentialsException e) {
			errorMessage = "用户名/密码错误";
		} catch (AuthenticationException e) {
			// 其他错误，比如锁定，如果想单独处理请单独catch处理
			errorMessage = "其他错误：" + e.getMessage();
		}
		if(errorMessage != null) {//出错了，返回登录页面
			model.addAttribute("errorMessage", errorMessage);
			return "shiroLogin";
        } else {//登录成功
        	return "redirect:/shiroController/main";
        }
	}
	@Autowired
	UserRealm userRealm;
	//测试权限注解
	@RequestMapping("/shiroController/update")
	@RequiresPermissions({"user:update1"})
	//@RequiresRoles()
	public String updateTest(){
		System.out.println("执行user:update.....");
		
		userRealm.clearCached();//手动调用方法，清除缓存
		return "shiro/main";
	}
	
	//测试session
	@RequestMapping("/shiroController/sessionTest")
	public String sessionTest(HttpSession session){
		session.setAttribute("key", "11111111232323231");
		shiroUserService.sessionTest();
		return "shiro/main";
	}
	//测试rememberMe
	@RequestMapping("/shiroController/rememberMeTest")
	public String rememberMeTest(){
		return "shiro/main";
	}
	
	//没有权限登录
	@RequestMapping("/shiroController/unauthorizedUrl")
	public String unauthorizedUrl(){
		return "shiro/message";
	}
}
