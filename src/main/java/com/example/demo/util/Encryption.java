package com.example.demo.util;

import java.security.MessageDigest;
import org.apache.tomcat.util.codec.binary.Base64;

@SuppressWarnings("deprecation")
public class Encryption {
	public static String toSHA1(String matkhau) {
		String salt = "addfegdv@fdgtt;fd.fdsfsdsdigfok12hf";
		String result = null;
		matkhau = matkhau + salt;
		try {
			byte[] dataBytes = matkhau.getBytes("UTF-8");
			MessageDigest md  = MessageDigest.getInstance("SHA-1");
			result = Base64.encodeBase64String(md.digest(dataBytes));
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		
	}
	public static void main(String[] args) {
		System.out.println(toSHA1("1234"));
	}

}
