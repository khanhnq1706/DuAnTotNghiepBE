package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.FirstTestDb;
import com.example.demo.respone.ApiRespone;
import com.example.demo.respone.FirstTestResponeDTO;
import com.example.demo.service.impl.FirstTestServiceImpl;

import java.util.List;

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
	public ApiRespone<FirstTestResponeDTO> getFirstTest(@PathVariable("id")  int id){
		ApiRespone<FirstTestResponeDTO> response = new ApiRespone<FirstTestResponeDTO>();
		response.setResult(firstTestServiceImpl.getFirstTestResponeDTOById(id));
		return response;
	}
	@GetMapping("firstTests")	
	public ApiRespone<List<FirstTestResponeDTO>> getFirstTests(){
		ApiRespone<List<FirstTestResponeDTO>> response = new ApiRespone<List<FirstTestResponeDTO>>();
		response.setResult(firstTestServiceImpl.findAll());
		return response;
	}
}
