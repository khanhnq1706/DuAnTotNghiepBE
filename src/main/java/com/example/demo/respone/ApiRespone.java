package com.example.demo.respone;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
@Builder
public class ApiRespone<T> {
	
	@Builder.Default
	private int code =1000;
	private String message;
	private T result;
	
	
}
