package com.example.demo.service;

import com.example.demo.entity.FoodEntity;
import com.example.demo.request.FoodRequestDTO;
import com.example.demo.respone.FoodResponeDTO;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface FoodService {
    public Page<FoodResponeDTO> getAllFood(int page, int size);
    public FoodResponeDTO saveFood(FoodRequestDTO requestDTO, MultipartFile file);
    public FoodResponeDTO updateFood(int idFood,FoodRequestDTO requestDTO, MultipartFile file);
    public FoodResponeDTO getFoodById(int idFood);
//	public Page<FoodResponeDTO> getFoodFromFilter(String nameFood, Integer idCategory, Boolean isSelling, Pageable pageable);
//	Page<FoodResponeDTO> getFoodFromFilter(Integer idCategory, Boolean isSelling, String nameFood, Pageable pageable);
	Page<FoodResponeDTO> getFoodFromFilter(String nameFood, String idCategory, String isSelling, Pageable pageable);
	
	
	
}
