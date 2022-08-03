package com.yahve.jwt.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.yahve.jwt.utils.JWTUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yahve.jwt.entity.UserInfo;
import com.yahve.jwt.service.UserInfoService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class UserInfoController {
	
	@Resource
	private UserInfoService userInfoService;
	
	@GetMapping("/api/login")
	public Map<String, Object> sysLogin(UserInfo user){
		
		log.info("userName [{}]" , user.getUserInfoName());
		log.info("userId: [{}]", user.getUserInfoId());
		Map<String, Object> map = new HashMap<>();


		try{
			UserInfo userInfoDB = userInfoService.userLogin(user);

			Map<String, String> payload = new HashMap<>();
			payload.put("userInfoId",userInfoDB.getUserInfoId());
			payload.put("userInfoName",userInfoDB.getUserInfoName());
			String token = JWTUtils.getToken(payload);
			map.put("state",true);
			map.put("msg","认证成功");
			map.put("token",token);

		}catch (Exception e){
			e.printStackTrace();
			map.put("state",false);
			map.put("msg","认证失败");
			map.put("token","");
		}

		return map;
		
		
	}



	@PostMapping("/api/test")
	public Map<String, Object> test(HttpServletRequest request){

		String token = request.getHeader("token");
		DecodedJWT decodedJWT = JWTUtils.verify(token);
		String userInfoId = decodedJWT.getClaim("userInfoId").asString();
		String userInfoName = decodedJWT.getClaim("userInfoName").asString();
		log.info("用户名：[{}]",userInfoName);
		log.info("ID : [{}]" , userInfoId);


		Map<String, Object> map = new HashMap<>();
		map.put("state",true);
		map.put("msg","请求成功");
		return map;


	}

}
