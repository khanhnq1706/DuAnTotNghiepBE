package com.example.demo.map;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.example.demo.entity.TableEntity;
import com.example.demo.enums.TableStatus;
import com.example.demo.request.TableRequestDTO;
import com.example.demo.respone.TableResponseDTO;

@Mapper(componentModel = "spring")
public interface TableMapper {

	TableEntity toTableEntity(TableRequestDTO request);

	@Mapping(target = "displayName", source = "status", qualifiedByName = "mapDisplayName")
	TableResponseDTO toTableResponseDTO(TableEntity tableStatus);

	@Named("mapDisplayName")
	default String mapDisplayName(TableStatus status) {
		switch (status) {
			case AVAILABLE:
				return "Bàn trống";
			case OCCUPIED:
				return "Đang phục vụ";
			case CLEANING:
				return "Đang dọn";
			case RESERVED:
				return "Đặt trước";
			case BILL_REQUESTED:
				return "Yêu cầu thanh toán";
			default:
				return "Không xác định";
		}
	}
}
