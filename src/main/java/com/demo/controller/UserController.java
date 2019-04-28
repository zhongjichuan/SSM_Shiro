package com.demo.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.demo.entity.User;
import com.demo.service.UserService;
import com.github.pagehelper.PageInfo;

@Controller
public class UserController {

	@Resource
	private UserService userService;
	
	@RequestMapping("/showUsers")
	public String userList(Model model,String pageNum){
		int page;
		if(pageNum == null){
			page = 0;
		}else{
			page = Integer.parseInt(pageNum);
		}
		PageInfo<User> userList = userService.selectWithPage(page);
		//List<User> userList = userService.selectAll();
		model.addAttribute("userList", userList);
		
		return "showList";
	} 
	@RequestMapping("/deleteUser/{name}")
	public String deleteUser(@PathVariable("name") String name,HttpServletRequest request){
		try {
			name=new String(name.getBytes("iso8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(name+"+++++++++++++++++++=");
		User user = new User();
		user.setName(name);
		int result = userService.deleteUser(user);
		System.out.println(result+"+++++++++++++++++++=");
		if(result > 0){
			return "redirect:/showUsers";
		}else{
			request.setAttribute("message", "删除失败!");
			return "redirect:/showUsers";
		}
	}
		
}
