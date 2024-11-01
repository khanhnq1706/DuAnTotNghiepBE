package com.example.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.request.PromotionRequestDTO;
import com.example.demo.respone.ApiRespone;
import com.example.demo.respone.PromotionResponeDTO;

import jakarta.validation.Valid;

public interface PromotionService {

	PromotionResponeDTO getFoodById(int idPromotion);

	Page<PromotionResponeDTO> getAllPromotion();

	PromotionResponeDTO savePromotion(PromotionRequestDTO requestDTO);

	PromotionResponeDTO updatePromotion(int idFood,PromotionRequestDTO requestDTO);

	PromotionResponeDTO deletePromotion(int idPromotion);

	Page<PromotionResponeDTO> getPromotionFromFilter(String namePromotion, Pageable pageable);

}
