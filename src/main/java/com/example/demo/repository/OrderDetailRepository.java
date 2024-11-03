package com.example.demo.repository;

import com.example.demo.entity.OrderDetailEntity;
import com.example.demo.respone.InvoiceDetailResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity,Integer> {
    @Query("select new com.example.demo.respone.InvoiceDetailResponseDTO" +
            "(oe.orderEntity.idOrder,oe.orderEntity.tableEntity.nameTable,oe.foodEntity.nameFood" +
            ",oe.foodEntity.priceFood,oe.foodEntity.discount,oe.quantity,oe.totalPrice,oe.orderEntity.total)" +
            "from OrderDetailEntity oe  where oe.orderEntity.idOrder= ?1"
    )
    public List<InvoiceDetailResponseDTO> getInvoiceDetailsByOrderId(Integer orderId);
}
