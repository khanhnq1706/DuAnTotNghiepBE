package com.example.demo.map;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.demo.entity.PromotionEntity;
import com.example.demo.request.PromotionRequestDTO;
import com.example.demo.respone.PromotionResponeDTO;
@Mapper(componentModel = "spring")
public interface PromotionMapper {
	@Mapping(source = "isIncreasePrice", target = "increasePrice")
	PromotionEntity toPromotionEntity(PromotionRequestDTO requestDTO);

	PromotionResponeDTO toPromotionResponeDTO(PromotionEntity promotionEntity);
}
