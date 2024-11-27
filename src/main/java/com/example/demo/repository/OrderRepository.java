package com.example.demo.repository;

import com.example.demo.entity.OrderDetailEntity;
import com.example.demo.entity.OrderEntity;
import com.example.demo.enums.OrderStatus;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {
        @Query("SELECT o FROM OrderEntity o WHERE " +
                        "(:statusOrder IS NULL OR o.statusOrder = :statusOrder) AND " +
                        "(:idOrder IS NULL OR o.idOrder = :idOrder) AND " +
                        "(:dateFrom IS NULL OR o.dateCreate >= :dateFrom) AND " +
                        "(:dateTo IS NULL OR o.dateCreate <= :dateTo) AND " +
                        "(:searchKeyword IS NULL OR " +
                        " CAST(o.total AS string) LIKE CONCAT('%', :searchKeyword, '%'))")
        Page<OrderEntity> filterOrders(
                        @Param("statusOrder") OrderStatus statusOrder,
                        @Param("idOrder") Integer idOrder,
                        @Param("dateFrom") Date dateFrom,
                        @Param("dateTo") Date dateTo,
                        @Param("searchKeyword") String searchKeyword,
                        Pageable pageable);
}
