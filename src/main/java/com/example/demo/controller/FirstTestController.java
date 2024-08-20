package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.FirstTestDb;
import com.example.demo.respone.ApiRespone;
import com.example.demo.service.impl.FirstTestServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("api")
public class FirstTestController {

	@Autowired
	FirstTestServiceImpl firstTestServiceImpl;
	
	@GetMapping("firstTest/{id}")	
	public ApiRespone<FirstTestDb> getFirstTest(@PathVariable("id")  int id){
		ApiRespone<FirstTestDb> response = new ApiRespone<FirstTestDb>();
		
		response.setResult(firstTestServiceImpl.getById(id));
		return response;
	}
	
}
