package com.example.demo.exception;

public enum ErrorCode{
	USER_EXISTED(1001,"User existed"),
	USER_NOT_EXISTS(1002,"User not existed"),
	PASSWORD_IS_INCORRECT(1003,"Password is incorrect");

	 ErrorCode(int code, String message) {
		this.code = code;
		this.message = message;
	}
		private int code;
		private String message;
		
	public int getCodes() {
		return code;
	}
	public void setCodes(int codes) {
		this.code = codes;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
