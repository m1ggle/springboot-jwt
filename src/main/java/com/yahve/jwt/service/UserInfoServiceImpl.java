package com.yahve.jwt.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yahve.jwt.dao.UserInfoDAO;
import com.yahve.jwt.entity.UserInfo;

@Service
public class UserInfoServiceImpl implements UserInfoService{

	@Resource
	private UserInfoDAO userInfoDAO;
	
	@Override
	public UserInfo userLogin(UserInfo userInfo) {
		// TODO Auto-generated method stub
		UserInfo userLogin = userInfoDAO.userLogin(userInfo);
		
		if(userLogin != null) {
			return userLogin;
		}
		
		throw new RuntimeException("认证失败");
	}

}
