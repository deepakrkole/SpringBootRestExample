package com.spring.security;

import java.io.IOException;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;

import com.spring.controller.*;
/**
 * 
 * @author DEEPAK
 *
 */

public class Authentications{
	public boolean checkAuthorization(HttpServletRequest request) throws IOException{
		boolean auth=false;
		if(request.getHeader("Authorization")==null){
			return false;
		}
		String basicEncodedString = request.getHeader("Authorization").substring(6);
		Base64.Decoder decoder = Base64.getDecoder();
		byte[] decodedByteArray = decoder.decode(basicEncodedString);
		String basicAuthAsString= new String(decodedByteArray);
		String []credentials=basicAuthAsString.split(":");
		if(credentials[0].equals("foo") && credentials[1].equals("bar")){
			auth=true;
		}
		//System.out.println(basicAuthAsString);
		
		return auth;
	}
}
