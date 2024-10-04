package com.example.demo.repository;

import com.example.demo.entity.FoodEntity;
import com.example.demo.respone.ApiRespone;
import com.example.demo.respone.FoodResponeDTO;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends JpaRepository<FoodEntity, Integer> {
    FoodEntity findByNameFood(String name);

	Page<FoodEntity> findAll(Pageable pageable);
	List<FoodEntity> findAll();

	Page<FoodEntity> findAll(Specification<FoodEntity> spec, Pageable pageable);

//	Page<FoodResponeDTO> findAll(Specification<FoodEntity> spec, Pageable pageable);


}
