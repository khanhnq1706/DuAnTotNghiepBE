package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.FirstTestDb;
import com.example.demo.repository.FirstTestDbRepository;

@Service
public class FirstTestServiceImpl {

	@Autowired
	FirstTestDbRepository firstTestDbRepository;
	
	public FirstTestDb getById(int id) {
		
		return firstTestDbRepository.findById(id).orElseThrow(()-> new RuntimeException("NULL_FT"));
	}
	
}
