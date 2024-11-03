package com.example.demo.util;

import java.security.MessageDigest;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SuppressWarnings("deprecation")
public class Encryption {
	public static String toSHA1(String matkhau) {
		String result = null;
		try {
			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
			result =  passwordEncoder.encode(matkhau);
			System.out.println("test1: "+ passwordEncoder.encode(matkhau));
			System.out.println("test2: "+ passwordEncoder.encode(matkhau));
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		
	}
	public static void main(String[] args) {
		System.out.println(toSHA1("1234"));
	}

}
