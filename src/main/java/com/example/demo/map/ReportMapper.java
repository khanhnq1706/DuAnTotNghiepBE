package com.example.demo.map;

import org.mapstruct.Mapper;

import com.example.demo.entity.ReportEntity;
import com.example.demo.request.ReportRequestDTO;
import com.example.demo.respone.ReportResponseDTO;

@Mapper(componentModel = "spring")
public interface ReportMapper {
//	ReportEntity toEntity(ReportRequestDTO dto);
//    ReportResponseDTO toDto(ReportEntity entity);
}
