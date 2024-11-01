package com.example.demo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.PromotionEntity;
import com.example.demo.map.PromotionMapper;
import com.example.demo.repository.PromotionRepository;
import com.example.demo.request.PromotionRequestDTO;
import com.example.demo.respone.ApiRespone;
import com.example.demo.respone.PromotionResponeDTO;
import com.example.demo.service.PromotionService;

import jakarta.validation.Valid;

@Service
public class PromotionServiceIml implements PromotionService{
	@Autowired
	PromotionRepository promotionRepository;
	@Autowired
	PromotionMapper mapper;
	@Override
	public PromotionResponeDTO getFoodById(int idPromotion) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<PromotionResponeDTO> getAllPromotion() {
		return new PageImpl<>(promotionRepository.findAll().stream().map(mapper::toPromotionResponeDTO).collect(Collectors.toList()));
	}
	@Transactional
	@Override
	public PromotionResponeDTO savePromotion(@Valid PromotionRequestDTO requestDTO) {
		PromotionEntity promotionEntity = promotionRepository.findByNamePromotion(requestDTO.getNamePromotion().trim());
		if (promotionEntity != null) {
			throw new RuntimeException("Promotion_already_exist");
		}
		promotionEntity = mapper.toPromotionEntity(requestDTO);
		return mapper.toPromotionResponeDTO(promotionRepository.save(promotionEntity));
	}

	@Override
	public PromotionResponeDTO updatePromotion(int idPromotion, @Valid PromotionRequestDTO requestDTO) {
	
		PromotionEntity promotionEntity = promotionRepository.findById(idPromotion)
				.orElseThrow(() -> new RuntimeException("Promotion_not_exist"));
		promotionEntity = promotionRepository.findByNamePromotion(requestDTO.getNamePromotion().trim());
		if (promotionEntity != null && promotionEntity.getIdPromotion()!=idPromotion ) {
			throw new RuntimeException("Promotion_already_exist");
		}
		promotionEntity = mapper.toPromotionEntity(requestDTO);
		promotionEntity.setIdPromotion(idPromotion);
		promotionRepository.save(promotionEntity);

		return mapper.toPromotionResponeDTO(promotionEntity);
	}

	@Override
	public PromotionResponeDTO deletePromotion(int idPromotion) {
		PromotionEntity promotionEntity = promotionRepository.findById(idPromotion)
                .orElseThrow(() -> new RuntimeException("Promotion_not_exist"));
    	promotionRepository.deleteById(idPromotion);
    	getAllPromotion();
	         return mapper.toPromotionResponeDTO(promotionEntity);
	         
	}

	@Override
	public Page<PromotionResponeDTO> getPromotionFromFilter(String namePromotion, Pageable pageable) {
		 try {
			 Specification<PromotionEntity> spec = Specification.where(null);

			    if (namePromotion != null && !namePromotion.isEmpty()) {
			        spec = spec.and((root, query, criteriaBuilder) ->
			                criteriaBuilder.like(root.get("namePromotion"), "%" + namePromotion + "%"));
			    }

			    Page<PromotionEntity> entities = promotionRepository.findAll(spec, pageable);

			    List<PromotionResponeDTO> prosDtos = entities.stream()
			            .map(mapper::toPromotionResponeDTO)
			            .collect(Collectors.toList());

			    return new PageImpl<>(prosDtos, pageable, entities.getTotalElements());
	        } catch (NumberFormatException e) {
	            throw new RuntimeException("Promotion_not_found");
	        }

}
	}
