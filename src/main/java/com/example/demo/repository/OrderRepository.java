package com.example.demo.repository;

import com.example.demo.entity.OrderDetailEntity;
import com.example.demo.entity.OrderEntity;
import com.example.demo.enums.OrderStatus;

import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {

	
	long count(Specification<OrderEntity> spec);

	List<OrderEntity> findAll(Specification<OrderEntity> spec);

	List<OrderEntity> findAllByStatusOrderOrderByDateModify(OrderStatus completed);
	
	@Query("SELECT o FROM OrderEntity o " +
            "WHERE o.statusOrder = :statusOrder " +
            "AND (:startDate IS NULL OR o.dateModify >= :startDate) " +
            "AND (:endDate IS NULL OR o.dateModify <= :endDate) " +
            "AND (:month IS NULL OR FUNCTION('MONTH', o.dateModify) = :month) " +
            "AND (:year IS NULL OR FUNCTION('YEAR', o.dateModify) = :year)" +
            "ORDER BY o.dateModify ASC")
    List<OrderEntity> findOrdersByCriteria(
            @Param("statusOrder") OrderStatus statusOrder,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate,
            @Param("month") Integer month,
            @Param("year") Integer year
    );
}
