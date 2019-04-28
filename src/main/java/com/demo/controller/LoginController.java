package com.demo.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.demo.entity.User;
import com.demo.service.UserService;

@Controller
public class LoginController {

	@Resource
	public UserService userSevice;

	@RequestMapping("/login")
	public String login(){
		return "login";
	}
	
	@RequestMapping("/LoginController/login")
	public String login(User user,HttpSession sessoin){
		User user1 = userSevice.validateUser(user);
		if(user1!=null){
			sessoin.setAttribute("user", user1);
			
			return "redirect:/showUsers";//重定向到controller
		}else{
			return "login";
		}
	}
}
