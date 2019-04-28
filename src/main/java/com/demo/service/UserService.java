package com.demo.service;

import java.util.List;

import com.demo.entity.User;
import com.github.pagehelper.PageInfo;

public interface UserService {

	public User validateUser(User user);
	public List<User> selectAll();
	public int deleteUser(User user);
	public PageInfo<User> selectWithPage(int page);
}
