package com.example.demo.repository;

import com.example.demo.entity.OrderEntity;
import com.example.demo.respone.InvoiceDetailResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity,Integer> {


}
