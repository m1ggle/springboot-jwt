package com.yahve.jwt.utils;

import java.util.Calendar;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JWTUtils {
	private static String SIGN = "nAxLN8JHOQEUiwagDY5VEPrXD9qf2gs2g1Ffj9_Uh7I";
	
	public static String getToken(Map<String, String> map) {
		Builder jwt = JWT.create();
		
		map.forEach(jwt::withClaim);
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 7);
		jwt.withExpiresAt(calendar.getTime());

		return jwt.sign(Algorithm.HMAC256(SIGN));
		
	}
	
	public static DecodedJWT verify(String token) {
		
		return JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
	}

}
