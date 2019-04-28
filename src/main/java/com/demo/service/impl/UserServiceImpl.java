package com.demo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import tk.mybatis.mapper.entity.Example;

import com.demo.entity.User;
import com.demo.mapper.UserMapper;
import com.demo.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
@Service("userService")
@Transactional(readOnly=true)
public class UserServiceImpl implements UserService{

	@Resource
	public UserMapper userMapper;
	@Override
	public User validateUser(User user) {
		Example example = new Example(User.class);
		//设置查询条件
		example.createCriteria().andCondition("name=", user.getName()).andCondition("password=",DigestUtils.md5DigestAsHex( user.getPassword().getBytes()));
		List<User> userList = userMapper.selectByExample(example );
		
		return userList!=null&&userList.size()>0?userList.get(0):null;
	}

	public List<User> selectAll(){
		
		return userMapper.findAll();
	}

	@Override
	public int deleteUser(User user) {
		int result = userMapper.deleteUser(user);
		return result;
	}

	//分页
	@Override
	public PageInfo<User> selectWithPage(int page) {
		int size = 5;
		PageHelper.startPage(page,5);
		List<User> userList = userMapper.findAll();
		PageInfo<User> users = new PageInfo<User>(userList);
		return users;
	}
	
}
