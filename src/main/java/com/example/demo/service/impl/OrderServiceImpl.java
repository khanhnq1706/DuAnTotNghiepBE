package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.enums.OrderStatus;
import com.example.demo.map.OrderMapper;
import com.example.demo.repository.*;
import com.example.demo.request.FoodRequestOrderDTO;
import com.example.demo.respone.OrderResponeDTO;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

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

    @Override
    public OrderResponeDTO saveOrder(List<FoodRequestOrderDTO> listFoodOrder, Integer idTable, String numbePhone) {
        TableEntity tableOrder = tableRepository
                .findById(idTable)
                .orElseThrow(() -> new RuntimeException("Table_not_exist"));
        CustomerEntity customerOrder = customerRepository.findByPhone(numbePhone).orElse(null);
        OrderEntity orderEntity = OrderEntity.builder()
                .statusOrder(OrderStatus.Waiting)
                .isPrinted(false)
                .tableEntity(tableOrder)
                .customer(customerOrder)
                .build();

        orderEntity = orderRepository.save(orderEntity);
        long totalPrice  =  0;
        for (FoodRequestOrderDTO foodRequestOrderDTO : listFoodOrder) {
            if (  foodRequestOrderDTO.getIdFood() == null){
                throw new RuntimeException("ID_FOOD_NOT_NULL");
            }
            if(foodRequestOrderDTO.getQuantity()<1){
                continue;
            }
            FoodEntity food = foodRepository.findById(foodRequestOrderDTO.getIdFood()).orElseThrow(() -> new RuntimeException("SOME_FOOD_NOT_EXISTS"));
            OrderDetailEntity orderDetail = OrderDetailEntity.builder()
                    .foodEntity(food)
                    .note(foodRequestOrderDTO.getNoteFood())
                    .quantity(foodRequestOrderDTO.getQuantity())
                    .price(food.getPriceFood() * foodRequestOrderDTO.getQuantity())
                    .orderEntity(orderEntity)
                    .build();
            orderDetail.setTotalPrice(orderDetail.getPrice() * (100 - food.getDiscount()) / 100);
            if(orderEntity.getTotal()==null){
                orderEntity.setTotal(orderDetail.getTotalPrice());
            } else {
                orderEntity.setTotal(orderEntity.getTotal()+orderDetail.getTotalPrice());
            }

            orderDetailRepository.save(orderDetail);
        }



        return orderMapper.toOrderResponeDTO(orderEntity);
    }

}
