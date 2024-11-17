package com.example.demo.repository;

import com.example.demo.entity.FoodEntity;
import com.example.demo.entity.OrderDetailEntity;
import com.example.demo.entity.OrderEntity;
import com.example.demo.entity.TableEntity;

import java.util.List;
import java.util.Optional;

import com.example.demo.respone.InvoiceDetailResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, Integer> {

    List<OrderDetailEntity> findByOrderEntity(OrderEntity orderEntity);

    List<OrderDetailEntity> findByOrderEntity_TableEntity(TableEntity tableEntity);
    @Query("select new com.example.demo.respone.InvoiceDetailResponseDTO" +
            "(oe.orderEntity.idOrder,oe.orderEntity.tableEntity.nameTable,oe.foodEntity.nameFood" +
            ",oe.foodEntity.priceFood,oe.foodEntity.discount,oe.quantity,oe.totalPrice,oe.orderEntity.total)" +
            "from OrderDetailEntity oe  where oe.orderEntity.idOrder= ?1"
    )
    public List<InvoiceDetailResponseDTO> getInvoiceDetailsByOrderId(Integer orderId);
    @Query("SELECT od FROM OrderDetailEntity od WHERE od.orderEntity = :orderEntity AND od.foodEntity = :foodEntity")
    Optional<OrderDetailEntity> findByOrderEntityAndFoodEntity(@Param("orderEntity") OrderEntity orderEntity, @Param("foodEntity") FoodEntity foodEntity);

}
