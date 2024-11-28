package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Shift;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Integer> {
    Optional<Shift> findByOrders_IdOrder(Integer idOrder);
    Shift findByIsWorking(Boolean isWorking);
}
