package com.example.demo.repository;

import com.example.demo.entity.OrderDetailEntity;
import com.example.demo.entity.OrderEntity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {
}
