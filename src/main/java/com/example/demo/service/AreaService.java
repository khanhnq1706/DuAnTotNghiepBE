package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.AreaEntity;
import com.example.demo.entity.CategoryFoodEntity;

public interface AreaService {
    public List<AreaEntity> findAll();
}
