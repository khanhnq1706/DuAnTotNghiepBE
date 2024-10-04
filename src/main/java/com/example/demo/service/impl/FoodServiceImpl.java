package com.example.demo.service.impl;

import com.example.demo.entity.CategoryFoodEntity;
import com.example.demo.entity.FoodEntity;
import com.example.demo.enums.ErrorEnum;
import com.example.demo.map.FoodMapper;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.FoodRepository;
import com.example.demo.request.FoodRequestDTO;
import com.example.demo.respone.FoodResponeDTO;
import com.example.demo.service.FoodService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

	@Autowired
	public FoodServiceImpl(FoodRepository foodRepository) {
		this.foodRepository = foodRepository;
	}

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
//	public Page<FoodResponeDTO> getFoodFromFilter(String nameFood,String idCategory, String isSelling,  Pageable pageable) {
//
//				List<FoodEntity> foodEntities = foodRepository.findAll();
//
//			    // Các điều kiện lọc khác
//				List<FoodEntity> fillteredList = foodEntities.stream()
//						.filter(food ->  !food.getIsDeleted())
//			            .filter(food  ->  food.getCategory().getIdCategory().equals(idCategory))
//			            .filter(food ->food.getIsSelling().equals(isSelling))
//			            .toList();
//		    List<FoodResponeDTO> foodDtos = foodEntities.stream()
//		            .map(foodMapper::toFoodResponeDTO)
//		            .filter(foodDto -> foodDto.getNameFood().contains(nameFood))
//		            .collect(Collectors.toList());
//		    return new PageImpl<>(foodDtos);
// 
//	}
    public Page<FoodResponeDTO> getFoodFromFilter(String nameFood, String idCategory, String isSelling, Pageable pageable) {
        String patterBoolean = "true|false";
        try {

            Integer categoryId = idCategory == null ? null : Integer.parseInt(idCategory);
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
            if (isSelling != null) {
                if (!isSelling.toLowerCase().matches(patterBoolean)) {
                    throw new RuntimeException("Invalid_is_Selling");
                }
                Boolean isSellingBool =  Boolean.parseBoolean(isSelling);
                foodEntities = foodEntities
                        .stream()
                        .filter(foodEntity -> foodEntity.getIsSelling() == isSellingBool).toList();
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


