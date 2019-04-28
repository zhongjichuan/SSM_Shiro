package com.demo.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import tk.mybatis.mapper.common.Mapper;

import com.demo.entity.User;

//通用mapper需要制定泛型，继承一个通用接口
public interface UserMapper extends Mapper<User>{

	public List<User> findAll();
	
	public int deleteUser(User user);
}
