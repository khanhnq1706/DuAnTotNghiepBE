package com.example.demo.service.impl;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.demo.request.VerifyTableRequestDTO;
import org.hibernate.mapping.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import com.example.demo.entity.AreaEntity;
import com.example.demo.entity.FoodEntity;
import com.example.demo.entity.FoodEntity;
import com.example.demo.entity.TableEntity;
import com.example.demo.enums.TableStatus;
import com.example.demo.map.TableMapper;
import com.example.demo.repository.AreaRepository;
import com.example.demo.repository.TableRepository;
import com.example.demo.request.TableRequestDTO;
import com.example.demo.request.TableStatusRequestDTO;
import com.example.demo.respone.ApiRespone;
import com.example.demo.respone.FoodResponeDTO;
import com.example.demo.respone.TableResponseDTO;
import com.example.demo.respone.TableStatusResponeDTO;
import com.example.demo.service.TableService;

import jakarta.validation.Valid;

@Service
public class TableServiceImpl implements TableService {

    @Autowired
    private TableRepository tableRepository;
    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private TableMapper tableMapper;

    // GetAll

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

    @Override
    public Page<TableResponseDTO> getAllTablesSortASC(int page, int size, int idArea) {
        Sort sort = Sort.by(Sort.Order.by("nameTable"));
        Pageable pageable = PageRequest.of(page, size);
        Page<TableEntity> sortedTablesPage = tableRepository.findAllSortedByNameTableASC(idArea, pageable);
        return sortedTablesPage.map(tableMapper::toTableResponseDTO);
    }

    @Override
    public Page<TableResponseDTO> getTablesFromFilter(String nameTable, TableStatus status, Integer idArea,
                                                      Pageable pageable) {
        // Sử dụng idArea để lọc bàn theo khu vực
        Page<TableEntity> tableEntities = tableRepository.findByFilters(nameTable, status, idArea, pageable);

        // Chuyển đổi sang DTO
        List<TableResponseDTO> tableDtos = tableEntities
                .stream()
                .map(tableMapper::toTableResponseDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(tableDtos, pageable, tableEntities.getTotalElements());
    }

    @Override
    public Page<TableResponseDTO> getAllTablesSortDESC(int page, int size, int idArea) {
        Sort sort = Sort.by(Sort.Order.by("nameTable"));
        Pageable pageable = PageRequest.of(page, size);
        Page<TableEntity> sortedTablesPage = tableRepository.findAllSortedByNameTableDESC(idArea, pageable);
        return sortedTablesPage.map(tableMapper::toTableResponseDTO);
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
        AreaEntity area = areaRepository.findById(request.getIdArea())
                .orElseThrow(() -> new RuntimeException("Area_not_found"));
        TableEntity newTable = tableMapper.toTableEntity(request);
        newTable.setArea(area);
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
        table.setLocked(request.isLocked());
        table.setNameTable(request.getNameTable().trim());
        return tableMapper.toTableResponseDTO(tableRepository.save(table));
    }

    // Delete
    @Override
    public ApiRespone<?> deleteTable(int idTable) {
        TableEntity table = tableRepository.findById(idTable)
                .orElseThrow(() -> new RuntimeException("Table_not_found"));
        tableRepository.deleteById(idTable);
        return ApiRespone.builder().message("Table delete successfully!").build();
    }

    @Override
    public ApiRespone<?> lockedTable(int idTable) {
        Optional<TableEntity> optionaltable = tableRepository.findById(idTable);
        if (!optionaltable.isPresent()) {
            return ApiRespone.builder()
                    .message("Table not found")
                    .build();
        }
        TableEntity table = optionaltable.get();
        table.setLocked(true);
        tableRepository.save(table);
        return ApiRespone.builder()
                .result(table)
                .message("Table locked successfully")
                .build();
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

    @Override
    public List<TableResponseDTO> findAllTableNotlocked() {
        return tableRepository.findByIsLocked(false).stream()
                .map(element -> tableMapper.toTableResponseDTO(element))
                .collect(Collectors.toList());
    }

    @Override
    public TableResponseDTO verifyTable(VerifyTableRequestDTO request) {
        TableEntity tableNeedVerify = tableRepository.findById(request.getIdTable())
                .orElseThrow(() -> new RuntimeException("Table_not_found"));
        if (tableNeedVerify.getSecretKey() - request.getSecretKey() != 0) {
            throw new RuntimeException("Table_key_expired");
        }
        return tableMapper.toTableResponseDTO(tableNeedVerify);
    }

}