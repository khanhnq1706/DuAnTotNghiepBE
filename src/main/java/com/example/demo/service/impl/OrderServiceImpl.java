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
import org.springframework.transaction.annotation.Transactional;

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
//    @Override
//    public OrderResponeDTO updateOrder(Integer idOrder, List<FoodRequestOrderDTO> requestOrderDTO) {
//        OrderEntity order = orderRepository
//                .findById(idOrder)
//                .orElseThrow(() -> new RuntimeException("Order_not_exist"));
//
//        long totalPrice = 0;
//
//        for (FoodRequestOrderDTO foodRequestOrderDTO : requestOrderDTO) {
//            if (foodRequestOrderDTO.getIdFood() == null) {
//                throw new RuntimeException("ID_FOOD_NOT_NULL");
//            }
//
//            FoodEntity food = foodRepository.findById(foodRequestOrderDTO.getIdFood())
//                    .orElseThrow(() -> new RuntimeException("SOME_FOOD_NOT_EXISTS"));
//            Optional<OrderDetailEntity> existingOrderDetail = orderDetailRepository
//                    .findByOrderEntityAndFoodEntity(order, food);
//
//            if (existingOrderDetail.isPresent()) {
//                OrderDetailEntity orderDetail = existingOrderDetail.get();
//                if (foodRequestOrderDTO.getQuantity() == 0) {
//                    orderDetailRepository.delete(orderDetail);
//                } else {
//                    orderDetail.setQuantity(foodRequestOrderDTO.getQuantity());
//                    orderDetail.setPrice(food.getPriceFood() * foodRequestOrderDTO.getQuantity());
//                    orderDetail.setTotalPrice(orderDetail.getPrice() * (100 - food.getDiscount()) / 100);
//                    orderDetail.setNote(foodRequestOrderDTO.getNoteFood());
//                    totalPrice += orderDetail.getTotalPrice();
//                    orderDetailRepository.save(orderDetail);
//                }
//            } else if (foodRequestOrderDTO.getQuantity() > 0) {
//                OrderDetailEntity orderDetail = OrderDetailEntity.builder()
//                        .foodEntity(food)
//                        .note(foodRequestOrderDTO.getNoteFood())
//                        .quantity(foodRequestOrderDTO.getQuantity())
//                        .price(food.getPriceFood() * foodRequestOrderDTO.getQuantity())
//                        .orderEntity(order)
//                        .build();
//                orderDetail.setTotalPrice(orderDetail.getPrice() * (100 - food.getDiscount()) / 100);
//                totalPrice += orderDetail.getTotalPrice();
//                orderDetailRepository.save(orderDetail);
//            }
//        }
//
//        order.setTotal((double) totalPrice);
//
//        if (orderDetailRepository.findByOrderEntity(order).isEmpty()) {
//            orderRepository.delete(order);
//            throw new RuntimeException("Order deleted as no items remain.");
//        }
//
//        return orderMapper.toOrderResponeDTO(order);
//    }

    @Override
    public OrderResponeDTO updateOrder(Integer idOrder, List<FoodRequestOrderDTO> requestOrderDTO) {
        // Step 1: Retrieve the order to update
        OrderEntity order = orderRepository
                .findById(idOrder)
                .orElseThrow(() -> new RuntimeException("Order_not_exist"));

        long totalPrice = 0;

        for (FoodRequestOrderDTO foodRequestOrderDTO : requestOrderDTO) {
            if (foodRequestOrderDTO.getIdFood() == null) {
                throw new RuntimeException("ID_FOOD_NOT_NULL");
            }

            // Step 2: Retrieve the food item
            FoodEntity food = foodRepository.findById(foodRequestOrderDTO.getIdFood())
                    .orElseThrow(() -> new RuntimeException("SOME_FOOD_NOT_EXISTS"));

            // Step 3: Check if food already exists in order details
            Optional<OrderDetailEntity> existingOrderDetail = orderDetailRepository
                    .findByOrderEntityAndFoodEntity(order, food);

            if (existingOrderDetail.isPresent()) {
                OrderDetailEntity orderDetail = existingOrderDetail.get();

                // Step 4: If quantity is 0, delete the item; otherwise, update it
                if (foodRequestOrderDTO.getQuantity() == 0) {
                    orderDetailRepository.delete(orderDetail);
                } else {
                    orderDetail.setQuantity(foodRequestOrderDTO.getQuantity());
                    orderDetail.setPrice(food.getPriceFood() * foodRequestOrderDTO.getQuantity());
                    orderDetail.setTotalPrice(orderDetail.getPrice() * (100 - food.getDiscount()) / 100);
                    orderDetail.setNote(foodRequestOrderDTO.getNoteFood());
                    totalPrice += orderDetail.getTotalPrice();
                    orderDetailRepository.save(orderDetail);
                }
            } else if (foodRequestOrderDTO.getQuantity() > 0) {
                // Step 5: If food item is new, create a new order detail
                OrderDetailEntity orderDetail = OrderDetailEntity.builder()
                        .foodEntity(food)
                        .note(foodRequestOrderDTO.getNoteFood())
                        .quantity(foodRequestOrderDTO.getQuantity())
                        .price(food.getPriceFood() * foodRequestOrderDTO.getQuantity())
                        .orderEntity(order)
                        .build();
                orderDetail.setTotalPrice(orderDetail.getPrice() * (100 - food.getDiscount()) / 100);
                totalPrice += orderDetail.getTotalPrice();
                orderDetailRepository.save(orderDetail);
            }
        }

        // Step 6: Update the total price of the order
        order.setTotal((double) totalPrice);

        // Step 7: If no items remain, delete the order
        if (orderDetailRepository.findByOrderEntity(order).isEmpty()) {
            orderRepository.delete(order);
            throw new RuntimeException("Order deleted as no items remain.");
        }

        // Step 8: Return the updated order response
        return orderMapper.toOrderResponeDTO(order);
    }

    @Override
    @Transactional
    public void deleteOrder(Integer idOrder) {
        OrderEntity order = orderRepository
                .findById(idOrder)
                .orElseThrow(() -> new RuntimeException("Order_not_exist"));
        System.out.println(idOrder);
        System.out.println(order);
        List<OrderDetailEntity> orderDetails = orderDetailRepository.findByOrderEntity(order);
        
        if (!orderDetails.isEmpty()) {
            orderDetailRepository.deleteAll(orderDetails);
        }
        orderRepository.delete(order);

      
        System.out.println("Order with ID " + idOrder + " and related order details were deleted successfully.");
    }





}
