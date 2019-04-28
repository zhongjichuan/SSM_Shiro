package com.demo.service;

import com.demo.entity.User;

public interface ShiroUserService {

	public User findUserByUsername(String username);
	
	public void sessionTest();
}
