package com.example.demo.service.impl;

import com.example.demo.entity.CategoryFoodEntity;
import com.example.demo.entity.FoodEntity;
import com.example.demo.map.FoodMapper;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.FoodRepository;
import com.example.demo.request.FoodRequestDTO;
import com.example.demo.respone.FoodResponeDTO;
import com.example.demo.service.FoodService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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

    @Transactional
    @Override
    public FoodResponeDTO saveFood(FoodRequestDTO requestDTO, MultipartFile file) {
        FoodEntity foodEntity = foodRepository.findByNameFood(requestDTO.getNameFood());
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
    public FoodResponeDTO updateFood(int idFood,FoodRequestDTO requestDTO, MultipartFile file) {
        CategoryFoodEntity categoryFood = categoryRepository.findById(requestDTO.getIdCategory())
                .orElseThrow(() -> new RuntimeException("Category_not_found"));
        FoodEntity foodEntity = foodRepository.findById(idFood)
                .orElseThrow(() -> new RuntimeException("FOOD_NOT_EXISTS"));
        String imgFoodTemp = foodEntity.getImgFood();

        foodEntity =foodMapper.toFoodEntity(requestDTO);

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

}
