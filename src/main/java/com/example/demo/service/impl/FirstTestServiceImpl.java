package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.FirstTestDb;
import com.example.demo.map.FirstTestMapper;
import com.example.demo.repository.FirstTestDbRepository;
import com.example.demo.respone.FirstTestResponeDTO;

@Service
public class FirstTestServiceImpl {

	@Autowired
	FirstTestDbRepository firstTestDbRepository;
	
	@Autowired
	FirstTestMapper firstTestMapper;
	
	public FirstTestDb getById(int id) {
		
		return firstTestDbRepository.findById(id).orElseThrow(()-> new RuntimeException("NULL_FT"));
	}
	public FirstTestResponeDTO getFirstTestResponeDTOById(int id) {
		
		return firstTestDbRepository.findById(id)
				.map(e -> firstTestMapper.toFirstTestResponeDTO(e))
				.orElseThrow(()-> new RuntimeException("NULL_FT"));
	}
	public List<FirstTestResponeDTO> findAll() {
		return firstTestDbRepository.findAll()
				.stream()
				.map(e -> firstTestMapper.toFirstTestResponeDTO(e))
				.toList();
	}
	
	
	
	
}
