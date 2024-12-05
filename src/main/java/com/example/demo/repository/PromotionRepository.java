package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.example.demo.entity.PromotionEntity;
@Repository
public interface PromotionRepository extends JpaRepository<PromotionEntity, Integer> {

	PromotionEntity findByNamePromotion(String name);
	PromotionEntity findByIdPromotion(Integer id);

	Page<PromotionEntity> findAll(Specification<PromotionEntity> spec, Pageable pageable);

}
