package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.enums.OrderStatus;
import com.example.demo.enums.TableStatus;
import com.example.demo.map.OrderMapper;
import com.example.demo.map.TableMapper;
import com.example.demo.repository.*;
import com.example.demo.request.FoodRequestOrderDTO;
import com.example.demo.respone.ApiRespone;
import com.example.demo.respone.OrderResponeDTO;
import com.example.demo.respone.TableResponseDTO;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Autowired
    TableMapper tableMapper;

    @Autowired
    ShiftRepository shiftRepository;

    @Override
    public ApiRespone<OrderResponeDTO> getOrder(int idOrder) {
        OrderEntity order = orderRepository.findById(idOrder)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        OrderResponeDTO responseDTO = orderMapper.toOrderResponeDTO(order);
        return ApiRespone.<OrderResponeDTO>builder()
                .result(responseDTO)
                .build();
    }

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
        tableOrder.setStatus(TableStatus.PENDING);
        orderEntity = orderRepository.save(orderEntity);
        tableOrder.setCurrentOrderId(orderEntity.getIdOrder());
        long totalPrice  =  0;
        for (FoodRequestOrderDTO foodRequestOrderDTO : listFoodOrder) {
            if (foodRequestOrderDTO.getIdFood() == null) {
                throw new RuntimeException("ID_FOOD_NOT_NULL");
            }
            if (foodRequestOrderDTO.getQuantity() < 1) {
                continue;
            }
            FoodEntity food = foodRepository.findById(foodRequestOrderDTO.getIdFood())
                    .orElseThrow(() -> new RuntimeException("SOME_FOOD_NOT_EXISTS"));
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

    @Override
    public OrderResponeDTO confirmOrder(Integer idOrder, Integer idShift, Integer idTable) {

        TableEntity tableOrder = tableRepository
                .findById(idTable)
                .orElseThrow(() -> new RuntimeException("Table_not_exist"));

        OrderEntity order = orderRepository
                .findById(idOrder)
                .orElseThrow(() -> new RuntimeException("Order_not_exist"));

        Shift shift = shiftRepository
                .findById(idShift)
                .orElseThrow(() -> new RuntimeException("Shift_not_exist"));

        order.setShift(shift);
        order.setStatusOrder(OrderStatus.Preparing); // Bếp đang chuẩn bị
        tableOrder.setStatus(TableStatus.OCCUPIED); // Bàn đang phục vụ

        orderRepository.save(order);
        tableRepository.save(tableOrder);
        return orderMapper.toOrderResponeDTO(order);
    }

}
