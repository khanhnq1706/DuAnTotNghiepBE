package com.example.demo.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.demo.enums.ErrorEnum;
import com.example.demo.respone.ApiRespone;

@ControllerAdvice
public class HandleException {

	@ExceptionHandler(value = RuntimeException.class)
	public ResponseEntity<ApiRespone<String>>  handleRunTimeException(RuntimeException exception) {

		ApiRespone<String> apiRespone = new ApiRespone<String>();
		String keyError = exception.getMessage();
		ErrorEnum error = ErrorEnum.valueOf(keyError);
		apiRespone.setCode(error.getCode());
		apiRespone.setMessage(error.getMessage());

		return ResponseEntity.badRequest().body(apiRespone);
	}

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<ApiRespone<String>>  handleValidException(MethodArgumentNotValidException exception) {

		ApiRespone<String> apiRespone = new ApiRespone<String>();
		String keyError = exception.getFieldError().getDefaultMessage();
		ErrorEnum error = ErrorEnum.valueOf(keyError);
		apiRespone.setCode(error.getCode());
		apiRespone.setMessage(error.getMessage());

		return ResponseEntity.badRequest().body(apiRespone);
	}
	
	
	

}
