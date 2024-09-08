package com.example.demo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.TableEntity;
import com.example.demo.map.TableMapper;
import com.example.demo.repository.TableRepository;
import com.example.demo.request.TableRequestDTO;
import com.example.demo.respone.TableResponseDTO;

@Service
public class TableService {

	@Autowired
	private TableRepository tableRepository;

	@Autowired
	private TableMapper tableMapper;

	public TableResponseDTO saveTable(TableRequestDTO request) {
		if (tableRepository.findByNameTable(request.getNameTable()) != null) {
			throw new RuntimeException("Table_exist");
		}
		return tableMapper.toTableResponseDTO(
				tableRepository.save(TableEntity.builder().nameTable(request.getNameTable()).isDeleted(true).build()));
	}

	public List<TableResponseDTO> getAllTable() {

		return tableRepository.findAll().stream().map(element -> tableMapper.toTableResponseDTO(element))
				.collect(Collectors.toList());
	}
	
	public TableResponseDTO updateTable(TableRequestDTO request,int idTable) {
		if (tableRepository.findByNameTable(request.getNameTable()) != null) {
			throw new RuntimeException("Table_exist");
		}
		TableEntity table = tableRepository.findById(idTable).orElseThrow(()->new RuntimeException("Table_not_exist"));
		table.setNameTable(request.getNameTable());
		table.setDeleted(request.isDeleted());
		return tableMapper.toTableResponseDTO(
				tableRepository.save(table));
	}
	
	

}
