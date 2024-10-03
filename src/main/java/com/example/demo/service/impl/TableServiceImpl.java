package com.example.demo.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.hibernate.mapping.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

import java.util.stream.Collectors;

import com.example.demo.entity.TableEntity;
import com.example.demo.enums.TableStatus;
import com.example.demo.map.TableMapper;
import com.example.demo.repository.TableRepository;
import com.example.demo.request.TableRequestDTO;
import com.example.demo.request.TableStatusRequestDTO;
import com.example.demo.respone.ApiRespone;
import com.example.demo.respone.TableResponseDTO;
import com.example.demo.respone.TableStatusResponeDTO;
import com.example.demo.service.TableService;

import jakarta.validation.Valid;

@Service
public class TableServiceImpl implements TableService {

    @Autowired
    private TableRepository tableRepository;

    @Autowired
    private TableMapper tableMapper;

    // GetAll
    @Override
    public List<TableResponseDTO> getAllTables() {
        return tableRepository.findAll().stream().map(element -> tableMapper.toTableResponseDTO(element))
                .collect(Collectors.toList());
    }

    @Override
    public ApiRespone<TableResponseDTO> getTable(int idtable) {
        TableEntity table = tableRepository.findById(idtable)
                .orElseThrow(() -> new RuntimeException("Table_not_found"));
        TableResponseDTO responseDTO = tableMapper.toTableResponseDTO(table);
        return ApiRespone.<TableResponseDTO>builder()
                .result(responseDTO)
                .build();
    }

    // GetPage
    @Override
    public Page<TableResponseDTO> getAllPages(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return tableRepository.findAll(pageable).map(tableMapper::toTableResponseDTO);
    }

    // save
    @Override
    public TableResponseDTO saveTables(TableRequestDTO request) {
        if (tableRepository.findByNameTable(request.getNameTable()) != null) {
            throw new RuntimeException("Table_exists");
        }
        if (request.getStatus() == null) {
            request.setStatus(TableStatus.AVAILABLE);
        }
        TableEntity newTable = tableMapper.toTableEntity(request);
        return tableMapper.toTableResponseDTO(tableRepository.save(newTable));
    }

    // Update
    @Override
    public TableResponseDTO updateTable(TableRequestDTO request, int idTable) {
        TableEntity table = tableRepository.findById(idTable)
                .orElseThrow(() -> new RuntimeException("Table_not_found"));
        if (!table.getNameTable().equals(request.getNameTable()) &&
                tableRepository.findByNameTable(request.getNameTable()) != null) {
            throw new RuntimeException("Table_exists");
        }
        table.setNameTable(request.getNameTable().trim());
        table.setDeleted(request.isDeleted());
        table.setStatus(request.getStatus());
        return tableMapper.toTableResponseDTO(tableRepository.save(table));
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
                .result(table)
                .message("Table deleted successfully")
                .build();
    }

    // Get All Table where table isdelete = false
    @Override
    public List<TableResponseDTO> findAllTableNotDelete() {
        return tableRepository.findByIsDeleted(false).stream()
                .map(element -> tableMapper.toTableResponseDTO(element))
                .collect(Collectors.toList());
    }

    @Override
    public List<TableStatusResponeDTO> getAllStatuses() {
        return Arrays.stream(TableStatus.values())
                .map(status -> new TableStatusResponeDTO(status.name(), tableMapper.mapDisplayName(status)))
                .collect(Collectors.toList());
    }

    @Override
    public ApiRespone<?> updateStatus(int idTable, TableStatusRequestDTO request) {
        TableEntity table = tableRepository.findById(idTable)
                .orElseThrow(() -> new RuntimeException("Table_not_found"));

        table.setStatus(request.getStatus()); // Cập nhật trạng thái mới
        TableEntity updatedTable = tableRepository.save(table); // Lưu thay đổi
        return ApiRespone.builder().result(tableMapper.toTableResponseDTO(updatedTable)).build(); // Trả về DTO sau
                                                                                                  // khi cập nhật

    }

    /****************** */

    @Override
    public TableResponseDTO searchTable(String name) {
        TableEntity entity = tableRepository.findByNameTable(name);
        return tableMapper.toTableResponseDTO(entity);
    }

    // @Override
    // public Page<TableResponseDTO> findAvailableTables(int numberOfGuests, int
    // page, int size) {
    // Pageable pageable = PageRequest.of(page, size);
    // return (Page<TableResponseDTO>)
    // tableRepository.findByStatusAndMaxCapacityLessThanEqual(TableStatus.AVAILABLE,
    // numberOfGuests, pageable)
    // .map(tableMapper::toTableResponseDTO);
    // }
    @Transactional
    @Override
    public Page<TableResponseDTO> findTablesByStatus(TableStatus status, int page, int size) {

        // Kiểm tra tính hợp lệ của page và size
        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException("Invalid page or size values");
        }

        Pageable pageable = PageRequest.of(page, size);
        return (Page<TableResponseDTO>) tableRepository.findByStatus(status, pageable)
                .map(tableMapper::toTableResponseDTO);
    }

}
