package com.pp.dao;

import org.apache.ibatis.annotations.Param;

import com.pp.entity.User;
import tk.mybatis.mapper.common.Mapper;

public interface UserMapper extends Mapper<User> {
	public User selectByUname(@Param("uname")String uname);
}