package com.example.demo.map;

import org.mapstruct.Mapper;

import com.example.demo.entity.TableEntity;
import com.example.demo.request.TableRequestDTO;
import com.example.demo.respone.TableResponseDTO;

@Mapper(componentModel = "spring")
public interface TableMapper {

	TableEntity toTableEntity(TableRequestDTO request);
	TableResponseDTO toTableResponseDTO(TableEntity TableEntity); 
	
	
}
