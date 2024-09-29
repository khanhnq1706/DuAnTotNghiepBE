package com.example.demo.service.impl;

import com.example.demo.entity.CategoryFoodEntity;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.CategoryService;
import jdk.jfr.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public List<CategoryFoodEntity> findAll() {
        return  categoryRepository.findAll();
    }

}
