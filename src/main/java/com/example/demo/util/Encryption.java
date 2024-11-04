package com.example.demo.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Encryption {
	public static String toSHA1(String matkhau) {
		String result = null;
		try {
			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
			result =  passwordEncoder.encode(matkhau);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		
	}

}
