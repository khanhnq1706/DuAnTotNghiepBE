package com.example.demo.repository;

import com.example.demo.entity.CategoryFoodEntity;
import com.example.demo.entity.FoodEntity;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends JpaRepository<FoodEntity, Integer> {
    FoodEntity findByNameFood(String name);


	Page<FoodEntity> findAll(Pageable pageable);
	List<FoodEntity> findAll();



    List<FoodEntity> findByCategory(CategoryFoodEntity category);

}
