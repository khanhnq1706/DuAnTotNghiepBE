package com.example.demo.repository;

import com.example.demo.entity.CategoryFoodEntity;
import com.example.demo.respone.FoodResponeDTO;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryFoodEntity,Integer> {
	
}

