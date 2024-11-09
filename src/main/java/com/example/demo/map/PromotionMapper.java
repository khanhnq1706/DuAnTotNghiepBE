package com.example.demo.map;

import org.mapstruct.Mapper;

import com.example.demo.entity.PromotionEntity;
import com.example.demo.request.PromotionRequestDTO;
import com.example.demo.respone.PromotionResponeDTO;
@Mapper(componentModel = "spring")
public interface PromotionMapper {
	PromotionEntity toPromotionEntity(PromotionRequestDTO requestDTO);

	PromotionResponeDTO toPromotionResponeDTO(PromotionEntity promotionEntity);
}
