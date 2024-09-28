package com.example.demo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

import java.util.stream.Collectors;

import com.example.demo.entity.TableEntity;
import com.example.demo.enums.TableStatus;
import com.example.demo.map.TableMapper;
import com.example.demo.repository.TableRepository;
import com.example.demo.request.TableRequestDTO;
import com.example.demo.respone.ApiRespone;
import com.example.demo.respone.TableResponseDTO;
import com.example.demo.service.TableService;

@Service
public class TableServiceImpl implements TableService {

    @Autowired
    private TableRepository tableRepository;

    @Autowired
    private TableMapper tableMapper;

    // save
    @Override
    public TableResponseDTO saveTables(TableRequestDTO request) {
        if (tableRepository.findByNameTable(request.getNameTable()) != null) {
            throw new RuntimeException("Table_exist");
        }
        TableEntity newTable = tableMapper.toTableEntity(request);
        TableEntity savedTable = tableRepository.save(newTable);
        return tableMapper.toTableResponseDTO(savedTable);
    }

    // GetAll
    @Override
    public List<TableResponseDTO> getAllTables() {
        return tableRepository.findAll().stream().map(element -> tableMapper.toTableResponseDTO(element))
                .collect(Collectors.toList());
    }

    // GetPage
    @Override
    public Page<TableResponseDTO> getAllPages(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return tableRepository.findAll(pageable).map(tableMapper::toTableResponseDTO);
    }


    // Update
    @Override
    public TableResponseDTO updateTable(TableRequestDTO request, int idTable) {
        TableEntity table = tableRepository.findById(idTable)
                .orElseThrow(() -> new RuntimeException("Table not found"));
        table.setNameTable(request.getNameTable());
        table.setDeleted(request.isDeleted());
        return tableMapper.toTableResponseDTO(
                tableRepository.save(table));
    }

    // Delete
    @Override
    public ApiRespone<?> deleteTable(int idTable) {
        Optional<TableEntity> optionaltable = tableRepository.findById(idTable);
        if (!optionaltable.isPresent()) {
            return ApiRespone.builder()
                    .message("Table not found")
                    .build();
        }
        TableEntity table = optionaltable.get();
        table.setDeleted(true);
        tableRepository.save(table);
        return ApiRespone.builder()
                .message("Table deleted successfully")
                .build();
    }

	@Override
	public TableResponseDTO searchTable(String name) {
			TableEntity entity = tableRepository.findByNameTable(name);
			return tableMapper.toTableResponseDTO(entity);
	
	}
	@Override
	public Page<TableResponseDTO> findAvailableTables( int page, int size) {
		 Pageable pageable = PageRequest.of(page, size);
	    return (Page<TableResponseDTO>) tableRepository.findByStatusAndAndIsDeletedFalse(TableStatus.AVAILABLE, pageable)
	            .map(tableMapper::toTableResponseDTO);
	}
	@Transactional
	@Override
	public Page<TableResponseDTO> findTablesByStatus(TableStatus status, int page, int size) {

	    // Kiểm tra tính hợp lệ của page và size
	    if (page < 0 || size <= 0) {
	        throw new IllegalArgumentException("Invalid page or size values");
	    }

	    Pageable pageable = PageRequest.of(page, size);
	    return (Page<TableResponseDTO>) tableRepository.findByStatusAndAndIsDeletedFalse(status, pageable)
	            .map(tableMapper::toTableResponseDTO);
	}

}
