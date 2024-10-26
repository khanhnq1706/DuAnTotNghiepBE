package com.example.demo.service.impl;

import com.example.demo.Specification.FoodSpecs;
import com.example.demo.entity.CategoryFoodEntity;
import com.example.demo.entity.FoodEntity;
import com.example.demo.map.FoodMapper;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.FoodRepository;
import com.example.demo.request.FoodRequestDTO;
import com.example.demo.respone.FoodResponeDTO;
import com.example.demo.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl implements FoodService {
	@Autowired
	private FoodRepository foodRepository;
	@Autowired
	private FoodMapper foodMapper;

	@Autowired
	private FileService fileService;

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public Page<FoodResponeDTO> getAllFood(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return foodRepository.findAll(pageable).map(foodMapper::toFoodResponeDTO);
	}

	@Override
	public FoodResponeDTO getFoodById(int idFood) {

		FoodEntity foodEntity = foodRepository.findById(idFood)
				.orElseThrow(() -> new RuntimeException(" FOOD_NOT_EXISTS"));
		FoodResponeDTO responeDTO = foodMapper.toFoodResponeDTO(foodEntity);
		responeDTO.setIdCategory(foodEntity.getCategory().getIdCategory());
		return responeDTO;
	}

	@Transactional
	@Override
	public FoodResponeDTO saveFood(FoodRequestDTO requestDTO, MultipartFile file) {
		FoodEntity foodEntity = foodRepository.findByNameFood(requestDTO.getNameFood().trim());
		CategoryFoodEntity categoryFood = categoryRepository.findById(requestDTO.getIdCategory())
				.orElseThrow(() -> new RuntimeException("Category_not_found"));
		if (foodEntity != null) {
			throw new RuntimeException("FOOD_ALREADY_EXISTS");
		}
		foodEntity = foodMapper.toFoodEntity(requestDTO);
		if (file != null) {
			System.out.println(file.getOriginalFilename());

			foodEntity.setImgFood(file.getOriginalFilename());
			fileService.saveFile(file);
		}
		foodEntity.setCategory(categoryFood);
		System.out.println(requestDTO.getDiscount());
		System.out.println(foodEntity.getDiscount());
		return foodMapper.toFoodResponeDTO(foodRepository.save(foodEntity));
	}

	@Transactional
	@Override
	public FoodResponeDTO updateFood(int idFood, FoodRequestDTO requestDTO, MultipartFile file) {
		CategoryFoodEntity categoryFood = categoryRepository.findById(requestDTO.getIdCategory())
				.orElseThrow(() -> new RuntimeException("Category_not_found"));
		FoodEntity foodEntity = foodRepository.findById(idFood)
				.orElseThrow(() -> new RuntimeException("FOOD_NOT_EXISTS"));
		String imgFoodTemp = foodEntity.getImgFood();
		foodEntity = foodRepository.findByNameFood(requestDTO.getNameFood().trim());
		if (foodEntity != null && foodEntity.getIdFood()!=idFood ) {
			throw new RuntimeException("FOOD_ALREADY_EXISTS");
		}
		foodEntity = foodMapper.toFoodEntity(requestDTO);

		if (file != null && !file.getOriginalFilename().trim().equals("")) {
			imgFoodTemp = file.getOriginalFilename();
			fileService.saveFile(file);
		}
		foodEntity.setCategory(categoryFood);
		foodEntity.setIdFood(idFood);
		foodEntity.setImgFood(imgFoodTemp);

		System.out.println(foodMapper.toFoodEntity(requestDTO).toString());
		System.out.println(foodEntity.toString());

		foodRepository.save(foodEntity);

		return foodMapper.toFoodResponeDTO(foodEntity);
	}

	@Override
	public Page<FoodResponeDTO> getFoodFromFilter(String nameFood, String idCategory, String isSelling, Pageable pageable) {

		Specification<FoodEntity> specsFood = Specification.where(
				FoodSpecs.hasNameFood(nameFood)
				.and(FoodSpecs.hasIdCategory(idCategory))
				.and(FoodSpecs.isSelling(isSelling)));
		return	foodRepository.findAll(specsFood, pageable).map(foodMapper::toFoodResponeDTO);

	}

	@Override
	public Page<FoodResponeDTO> getFoodFromFilter(String nameFood, String idCategory, Pageable pageable) {
		  try {

	            Integer categoryId = idCategory == null ? null : Integer.parseInt(idCategory);
	            System.out.println("idCategory"+categoryId);
	            List<FoodEntity> foodEntities = foodRepository.findAll();
	            if (categoryId != null) {
	                foodEntities = foodEntities
	                        .stream()
	                        .filter(foodEntity -> foodEntity.getCategory().getIdCategory() == categoryId).toList();
	            }
	            if (nameFood != null) {
	                foodEntities = foodEntities
	                        .stream()
	                        .filter(foodEntity -> foodEntity.getNameFood().contains(nameFood)).toList();
	            }

	            List<FoodResponeDTO> foodDtos = foodEntities.stream()
	                    .map(foodMapper::toFoodResponeDTO)
	                    .collect(Collectors.toList());
	            return new PageImpl<>(foodDtos);
	        } catch (NumberFormatException e) {
	            throw new RuntimeException("Invalid_id_Category");
	        }
	}



}

