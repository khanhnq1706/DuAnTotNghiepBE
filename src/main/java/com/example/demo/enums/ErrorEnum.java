package com.example.demo.enums;


public enum ErrorEnum {
	NULL_FT(1101,"First test null"),
	
	// Error code table : 100*
	Table_exist(1001,"Table exist in database"),Table_not_exist(1002,"Table not exist in database")
	// erroe code qr : 120*
	,QR_exist(1201,"Qr exist in database")
	;
	private int code;
	private String message;
	private ErrorEnum(int code, String message) { 
		this.code = code;
		this.message = message;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	
	

}
