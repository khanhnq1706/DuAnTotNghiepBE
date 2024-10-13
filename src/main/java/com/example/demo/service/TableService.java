package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.enums.TableStatus;

import com.example.demo.request.TableRequestDTO;
import com.example.demo.respone.ApiRespone;
import com.example.demo.respone.TableResponseDTO;

public interface TableService {
	TableResponseDTO saveTables(TableRequestDTO request);

	TableResponseDTO updateTable(TableRequestDTO request, int idTable);

	ApiRespone<?> deleteTable(int idTable);

	List<TableResponseDTO> getAllTables();

	Page<TableResponseDTO> getAllPages(int page, int size);

	
	

//	Page<TableResponseDTO> getTablesFromFilter(String nameTable, TableStatus status, String location,
//			Pageable pageable);

	Page<TableResponseDTO> getTablesFromFilter(String nameTable, String status, String location, Pageable pageable);
}
