package com.example.demo.repository;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.FoodEntity;

@CrossOrigin("http://localhost:4200")
public interface FoodFindRepository extends JpaRepository<FoodEntity, Integer> {
    Page<FoodEntity> findByCategoryIdCategory(@Param("idCategory") Integer idCategory, Pageable pageable);
    Page<FoodEntity> findByNameFoodContaining(String nameFood, Pageable pageable);
}
