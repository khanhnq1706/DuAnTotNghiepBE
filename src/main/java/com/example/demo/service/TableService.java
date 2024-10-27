package com.example.demo.service;

import java.util.List;

import com.example.demo.request.VerifyTableRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.enums.TableStatus;

import com.example.demo.request.TableRequestDTO;
import com.example.demo.request.TableStatusRequestDTO;
import com.example.demo.respone.ApiRespone;
import com.example.demo.respone.TableResponseDTO;
import com.example.demo.respone.TableStatusResponeDTO;

public interface TableService {
	TableResponseDTO saveTables(TableRequestDTO request);

	TableResponseDTO updateTable(TableRequestDTO request, int idTable);

	ApiRespone<?> deleteTable(int idTable);

	Page<TableResponseDTO> getAllPages(int page, int size);

	ApiRespone<?> lockedTable(int idTable);

	ApiRespone<TableResponseDTO> getTable(int idtable);

	List<TableStatusResponeDTO> getAllStatuses();

	public ApiRespone<?> updateStatus(int idTable, TableStatusRequestDTO request);

	public List<TableResponseDTO> findAllTableNotlocked();

	public Page<TableResponseDTO> getAllTablesSortASC(int page, int size, int idArea);

	public Page<TableResponseDTO> getAllTablesSortDESC(int page, int size, int idArea);

	// Page<TableResponseDTO> getTablesFromFilter(String nameTable, String status,
	// Pageable pageable);

	public Page<TableResponseDTO> getTablesFromFilter(String nameTable, TableStatus status, Integer idArea,
			Pageable pageable);

	public TableResponseDTO  verifyTable(VerifyTableRequestDTO Request);
}
