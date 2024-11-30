package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.demo.entity.*;
import com.example.demo.enums.PaymentMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.enums.TableStatus;
import com.example.demo.map.OrderMapper;
import com.example.demo.map.TableMapper;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.FoodRepository;
import com.example.demo.repository.OrderDetailRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ShiftRepository;
import com.example.demo.repository.TableRepository;
import com.example.demo.request.FoodRequestOrderDTO;
import com.example.demo.respone.OrderResponeDTO;
import com.example.demo.service.SeparateAndMergeOrderService;

import jakarta.transaction.Transactional;
@Service
public class SeparateAndMergeOrderServiceImpl implements SeparateAndMergeOrderService {
	 @Autowired
     OrderRepository orderRepository;

     @Autowired
     OrderDetailRepository orderDetailRepository;

     @Autowired
     TableRepository tableRepository;

     @Autowired
     CustomerRepository customerRepository;

     @Autowired
     FoodRepository foodRepository;

     @Autowired
     OrderMapper orderMapper;

     @Autowired
     TableMapper tableMapper;

     @Autowired
     ShiftRepository shiftRepository;
     @Override
     public OrderResponeDTO updateOrderAll(Integer idOrder, List<FoodRequestOrderDTO> requestOrderDTO) {
    	    OrderEntity order = orderRepository.findById(idOrder)
    	            .orElseThrow(() -> new RuntimeException("Order_not_exist"));

    	    List<OrderDetailEntity> orderDetails = new ArrayList<>();
    	    long totalPrice = 0;

		 Shift shift = shiftRepository
				 .findByIsWorking(true);
		 if (shift == null) {
			 throw new RuntimeException("Shift_not_exist");
		 }

    	    for (FoodRequestOrderDTO foodRequestOrderDTO : requestOrderDTO) {
    	        FoodEntity food = foodRepository.findById(foodRequestOrderDTO.getIdFood())
    	                .orElseThrow(() -> new RuntimeException("SOME_FOOD_NOT_EXISTS"));

    	        Optional<OrderDetailEntity> existingOrderDetail = orderDetailRepository
    	                .findByOrderEntityAndFoodEntity(order, food);

    	        if (existingOrderDetail.isPresent()) {
    	            OrderDetailEntity orderDetail = existingOrderDetail.get();
    	            if (foodRequestOrderDTO.getQuantity() == 0) {
    	                orderDetailRepository.delete(orderDetail);
    	            } else {
    	                orderDetail.setQuantity(foodRequestOrderDTO.getQuantity());
    	                orderDetail.setPrice(food.getPriceFood());
    	                orderDetail.setTotalPrice(calculateTotalPrice(foodRequestOrderDTO.getQuantity(), food.getPriceFood(), food.getDiscount()));
    	                orderDetail.setNote(foodRequestOrderDTO.getNoteFood());
    	                orderDetails.add(orderDetail);
    	                totalPrice += orderDetail.getTotalPrice();
    	            }
    	        } else if (foodRequestOrderDTO.getQuantity() > 0) {
    	            OrderDetailEntity orderDetail = OrderDetailEntity.builder()
    	                    .foodEntity(food)
    	                    .note(foodRequestOrderDTO.getNoteFood())
    	                    .quantity(foodRequestOrderDTO.getQuantity())
    	                    .price(food.getPriceFood())
    	                    .orderEntity(order)
    	                    .build();
    	            orderDetail.setTotalPrice(calculateTotalPrice(foodRequestOrderDTO.getQuantity(), food.getPriceFood(), food.getDiscount()));
    	            orderDetails.add(orderDetail);
    	            totalPrice += orderDetail.getTotalPrice();
    	        }
    	    }

    	    orderDetailRepository.saveAll(orderDetails);
    	    order.setTotal((double) totalPrice);
			order.setShift(shift);
			order.setNamePaymentMethod(PaymentMethod.empty.getName());

    	    if (orderDetails.isEmpty()) {
    	        orderRepository.delete(order);
    	        throw new RuntimeException("Order deleted as no items remain.");
    	    }

    	    orderRepository.save(order);
    	    return orderMapper.toOrderResponeDTO(order);
    	}

    	private long calculateTotalPrice(int quantity, double price, double discount) {
    	    return (long) (quantity * price * (100 - discount) / 100);
    	}

     @Override
     @Transactional
     public void deleteOrder(Integer idOrder) {
         OrderEntity order = orderRepository
                 .findById(idOrder)
                 .orElseThrow(() -> new RuntimeException("Order_not_exist"));
         TableEntity table = order.getTableEntity();
         List<OrderDetailEntity> orderDetails = orderDetailRepository.findByOrderEntity(order);
         if (!orderDetails.isEmpty()) {
             orderDetailRepository.deleteAll(orderDetails);
         }
         orderRepository.delete(order);


         if (table != null) {
             table.setStatus(TableStatus.AVAILABLE);
             table.setCurrentOrderId(null);
			 table.setCurrentIP(null);
             tableRepository.save(table); 
         }

     }
}
