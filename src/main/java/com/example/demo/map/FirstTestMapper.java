package com.example.demo.map;

import org.mapstruct.Mapper;


import com.example.demo.entity.FirstTestDb;
import com.example.demo.request.FirstTestRequestDTO;
import com.example.demo.respone.FirstTestResponeDTO;

@Mapper(componentModel = "spring") 
public interface FirstTestMapper {
	FirstTestDb toFirstTestDB(FirstTestRequestDTO request);
	FirstTestResponeDTO toFirstTestResponeDTO(FirstTestDb firstTestDb);
}
