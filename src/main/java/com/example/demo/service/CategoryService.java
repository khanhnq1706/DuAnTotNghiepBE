package com.example.demo.service;


import com.example.demo.entity.CategoryFoodEntity;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    public List<CategoryFoodEntity> findAll();
    public CategoryFoodEntity findByidCategory(int id);
}
