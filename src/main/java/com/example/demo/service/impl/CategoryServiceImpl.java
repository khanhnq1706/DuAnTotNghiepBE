package com.example.demo.service.impl;


import com.example.demo.entity.CategoryFoodEntity;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.CategoryService;
import jdk.jfr.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public List<CategoryFoodEntity> findAll() {
        return  categoryRepository.findAll();
    }
	@Override
	public CategoryFoodEntity findByidCategory(int id) {
		return categoryRepository.findById(id).orElse(null);
	}

    


}
