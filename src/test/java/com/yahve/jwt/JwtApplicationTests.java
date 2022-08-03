package com.yahve.jwt;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

@SpringBootTest
class JwtApplicationTests {

    @Test
    void contextLoads() {
    	Calendar instance = Calendar.getInstance();
    	final String SGIN = "nAxLN8JHOQEUiwagDY5VEPrXD9qf2gs2g1Ffj9_Uh7I";
    	
    	instance.add(Calendar.SECOND,200);
    	Map<String, Object> map = new HashMap<>();
		String token = JWT.create()
		.withHeader(map)
		.withClaim("UserInfoId", "001")
		.withClaim("UserInfoName", "yahve")
		.withExpiresAt(instance.getTime())
		.sign(Algorithm.HMAC256(SGIN));
		
		System.out.println(token);
    }
    
    @Test
    public void testToken() {
    	final String SGIN = "nAxLN8JHOQEUiwagDY5VEPrXD9qf2gs2g1Ffj9_Uh7I";
    	String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJVc2VySW5mb0lkIjoiMDAxIiwiVXNlckluZm9OYW1lIjoieWFodmUiLCJleHAiOjE2NTk1MDMwMzB9.nw-2T7b5yGQi4lgO8FEKx-ivpLpuHgm9wc33l9--7wg";
    	
    	JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SGIN)).build();
    	DecodedJWT decodedJWT = verifier.verify(token);
    	System.out.println(decodedJWT.getClaim("UserInfoId").toString());
    	System.out.println(decodedJWT.getClaim("UserInfoName").toString());
    }
}
