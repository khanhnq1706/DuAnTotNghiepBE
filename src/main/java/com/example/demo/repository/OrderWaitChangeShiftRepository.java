package com.example.demo.repository;

import com.example.demo.entity.OrderWaitChangeShiftEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderWaitChangeShiftRepository extends JpaRepository<OrderWaitChangeShiftEntity, Integer> {
}
