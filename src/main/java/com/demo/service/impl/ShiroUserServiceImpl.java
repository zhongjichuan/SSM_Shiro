package com.demo.service.impl;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import com.demo.entity.User;
import com.demo.mapper.UserMapper;
import com.demo.service.ShiroUserService;

@Service("shiroUserService")
public class ShiroUserServiceImpl implements ShiroUserService {

	@Resource
	public UserMapper userMapper;
	
	@Override
	public User findUserByUsername(String username) {
		User user = userMapper.selectByPrimaryKey(username);
		return user;
	}

	@Override
	public void sessionTest() {
		//在service中使用session
		Session session = SecurityUtils.getSubject().getSession();
		System.out.println(session.getAttribute("key"));
		
	}

}
