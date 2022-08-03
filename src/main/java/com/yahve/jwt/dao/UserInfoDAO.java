package com.yahve.jwt.dao;

import org.apache.ibatis.annotations.Mapper;

import com.yahve.jwt.entity.UserInfo;

@Mapper
public interface UserInfoDAO {
	
	UserInfo userLogin(UserInfo userInfo);

}
