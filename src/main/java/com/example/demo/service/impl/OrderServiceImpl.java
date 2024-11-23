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

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
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
        public OrderResponeDTO saveOrder(List<FoodRequestOrderDTO> listFoodOrder, Integer idTable, String numbePhone,
                                         String ipCustomer, OrderStatus status) {
                TableEntity tableOrder = tableRepository
                        .findById(idTable)
                        .orElseThrow(() -> new RuntimeException("Table_not_exist"));
                if (tableOrder.getCurrentOrderId() != null) {
                        if (tableOrder.getCurrentIP() == null || !tableOrder.getCurrentIP().equals(ipCustomer)) {
                                throw new RuntimeException("Table_being_served");
                        }
                }
                CustomerEntity customerOrder = customerRepository.findByPhone(numbePhone).orElse(null);
                OrderEntity orderEntity = OrderEntity.builder()
                        .statusOrder(status)
                        .isPrinted(false)
                        .tableEntity(tableOrder)
                        .customer(customerOrder)
                        .build();
                if (orderEntity.getStatusOrder() == OrderStatus.Waiting) {
                        tableOrder.setStatus(TableStatus.PENDING);
                } else if (orderEntity.getStatusOrder() == OrderStatus.Preparing) {
                        tableOrder.setStatus(TableStatus.OCCUPIED);
                }
                orderEntity = orderRepository.save(orderEntity);
                tableOrder.setCurrentOrderId(orderEntity.getIdOrder());
                tableOrder.setCurrentIP(ipCustomer);

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
                                .price(food.getPriceFood())
                                .orderEntity(orderEntity)
                                .build();

                        orderDetail.setTotalPrice(orderDetail.getPrice() * orderDetail.getQuantity()
                                * (100 - food.getDiscount()) / 100);

                        orderEntity.setTotal(orderEntity.getTotal() + orderDetail.getTotalPrice());
                        System.out.println("Plio : " + orderDetail.getTotalPrice());

                        orderDetailRepository.save(orderDetail);
                }
                return orderMapper.toOrderResponeDTO(orderEntity);
        }

        @Override
        public OrderResponeDTO confirmOrder(Integer idOrderOld, Integer idOrderNew, Integer idShift) {
                OrderEntity mainOrder = orderRepository
                        .findById(idOrderOld)
                        .orElseThrow(() -> new RuntimeException("Order_not_exist"));

                TableEntity tableOrder = mainOrder.getTableEntity();

                Shift shift = shiftRepository
                        .findById(idShift)
                        .orElseThrow(() -> new RuntimeException("Shift_not_exist"));

                if (tableOrder.getCurrentOrderId() != null && idOrderNew != null) {
                        OrderEntity subOrder = orderRepository
                                .findById(idOrderNew)
                                .orElseThrow(() -> new RuntimeException("Order_not_exist"));
                        mergeOrderDetails(mainOrder, subOrder);

                        mainOrder.setStatusOrder(OrderStatus.Preparing);
                        subOrder.setStatusOrder(OrderStatus.Cancelled);
                        tableOrder.setStatus(TableStatus.OCCUPIED);
                        mainOrder.setTotal(subOrder.getTotal() + mainOrder.getTotal());
                        orderRepository.save(mainOrder);
                        tableOrder.setCurrentOrderId(mainOrder.getIdOrder());
                } else {
                        mainOrder.setShift(shift);
                        mainOrder.setStatusOrder(OrderStatus.Preparing);
                        tableOrder.setStatus(TableStatus.OCCUPIED);
                        orderRepository.save(mainOrder);
                }
                tableRepository.save(tableOrder);
                System.out.println("nooooo" + tableOrder.getCurrentOrderId());
                return orderMapper.toOrderResponeDTO(mainOrder);
        }

        // Hàm gộp chi tiết đơn hàng
        private void mergeOrderDetails(OrderEntity mainOrder, OrderEntity subOrder) {
                List<OrderDetailEntity> newDetailsToAdd = new ArrayList<>();
                for (Iterator<OrderDetailEntity> subOrderIterator = subOrder.getListOrderDetail()
                        .iterator(); subOrderIterator.hasNext();) {
                        OrderDetailEntity currentDetail = subOrderIterator.next();
                        boolean isExisting = false;

                        // Duyệt qua các chi tiết món ăn từ đơn hàng chính (mainOrder)
                        for (OrderDetailEntity detail : mainOrder.getListOrderDetail()) {
                                // Kiểm tra nếu món ăn trùng
                                if (currentDetail.getFoodEntity().getIdFood() == detail.getFoodEntity().getIdFood()) {
                                        // Nếu món ăn trùng, gộp số lượng và tổng giá
                                        detail.setQuantity(currentDetail.getQuantity() + detail.getQuantity());
                                        detail.setTotalPrice(currentDetail.getTotalPrice() + detail.getTotalPrice());
                                        isExisting = true;

                                        // Xóa món ăn đã trùng khỏi subOrder
                                        subOrderIterator.remove();
                                        break;
                                }
                        }

                        // Nếu món ăn chưa tồn tại trong đơn hàng chính, thêm vào danh sách để thêm mới
                        if (!isExisting) {
                                currentDetail.setOrderEntity(mainOrder);
                                newDetailsToAdd.add(currentDetail);
                        }
                }

                mainOrder.getListOrderDetail().addAll(newDetailsToAdd);
                orderRepository.save(mainOrder);
        }

        @Override
        public OrderResponeDTO updateOrder(Integer idOrder, FoodRequestOrderDTO foodOrder) {
                OrderEntity order = orderRepository.findById(idOrder)
                        .orElseThrow(() -> new RuntimeException("Order_not_found"));

                if (foodOrder.getIdFood() == null) {
                        throw new RuntimeException("ID_FOOD_NULL");
                }

                FoodEntity foodEntity = foodRepository.findById(foodOrder.getIdFood())
                        .orElseThrow(() -> new RuntimeException("SOME_FOOD_NOT_EXISTS"));
                Optional<OrderDetailEntity> existingOrderDetail = orderDetailRepository
                        .findByOrderEntityAndFoodEntity(order, foodEntity);
                OrderDetailEntity orderDetail;
                if (existingOrderDetail.isPresent()) {
                        orderDetail = existingOrderDetail.get();
                        orderDetail.setQuantity(orderDetail.getQuantity() + foodOrder.getQuantity());
                        orderDetail.setTotalPrice(orderDetail.getPrice() * orderDetail.getQuantity()
                                * (100 - foodEntity.getDiscount()) / 100);

                        System.out.println("Trùng");
                } else {
                        orderDetail = OrderDetailEntity.builder().foodEntity(foodEntity)
                                .note(foodOrder.getNoteFood())
                                .quantity(foodOrder.getQuantity())
                                .price(foodEntity.getPriceFood())
                                .orderEntity(order)
                                .build();
                        orderDetail.setTotalPrice(orderDetail.getPrice() * orderDetail.getQuantity()
                                * (100 - foodEntity.getDiscount()) / 100);
                        orderDetailRepository.save(orderDetail);
                }
                order.setTotal(order.getListOrderDetail().stream()
                        .mapToDouble(OrderDetailEntity::getTotalPrice).sum());
                orderRepository.save(order);
                return orderMapper.toOrderResponeDTO(order);
        }
 
        @Override
        public ApiRespone<?> removeOrderdetail(int idOrderDetail) {
                OrderDetailEntity orderdetail = orderDetailRepository.findById(idOrderDetail)
                        .orElseThrow(() -> new RuntimeException("IdOrderDetail_NULL"));
                OrderEntity order = orderdetail.getOrderEntity();
                try {
                        order.setTotal(order.getTotal() - orderdetail.getTotalPrice());
                        orderRepository.save(order);
                        orderDetailRepository.deleteById(idOrderDetail);

                        List<OrderDetailEntity> listOrrderdetail = orderDetailRepository.findByOrderEntity(order);
                        if (listOrrderdetail == null || listOrrderdetail.isEmpty()) {
                                order.setStatusOrder(OrderStatus.Cancelled);
                                order.getTableEntity().setStatus(TableStatus.AVAILABLE);
                                order.getTableEntity().setCurrentOrderId(null);
                                order.getTableEntity().setCurrentIP(null);
                                orderRepository.save(order);
                        }
                        return ApiRespone.builder().message("Delete successfully!").build();
                } catch (Exception e) {
                        return ApiRespone.builder().message("Delete fail!").build();
                }
        }

        @Override
        public OrderResponeDTO updateQuantityOrderDetails(int idOrder, int idOrderdetail, int newQuantity) {
                OrderEntity orderEntity = orderRepository.findById(idOrder)
                        .orElseThrow(() -> new RuntimeException("Order_not_exist"));
                OrderDetailEntity orderdetail = orderDetailRepository.findById(idOrderdetail)
                        .orElseThrow(() -> new RuntimeException("OrderDetail_not_found"));
                if (newQuantity < 1) {
                        throw new RuntimeException("Quantity_must_be_positive");
                }
                orderdetail.setQuantity(newQuantity);
                orderdetail.setTotalPrice(orderdetail.getPrice() * newQuantity
                        * (100 - orderdetail.getFoodEntity().getDiscount()) / 100);
                orderEntity.setTotal(orderEntity.getListOrderDetail().stream()
                        .mapToDouble(OrderDetailEntity::getTotalPrice).sum());

                orderDetailRepository.save(orderdetail);
                orderRepository.save(orderEntity);
                return orderMapper.toOrderResponeDTO(orderEntity);
        }

        @Override
        public ApiRespone<?> cancelOrder(Integer idOrderOld, Integer idOrderNew, String cancellationReason) {
                // Tìm đơn phụ (idOrderNew), nếu có
                OrderEntity subOrder = orderRepository.findById(idOrderNew)
                        .orElseThrow(() -> new RuntimeException("Sub-order not found"));
                if (idOrderOld == 0) {

                        // Cập nhật trạng thái đơn phụ thành 'Cancelled'
                        subOrder.setStatusOrder(OrderStatus.Cancelled);
                        subOrder.setCancellationReason(cancellationReason);
                        subOrder.getTableEntity().setStatus(TableStatus.AVAILABLE);
                        subOrder.getTableEntity().setCurrentOrderId(null);
                        orderRepository.save(subOrder);
                } else {
                        OrderEntity mainOrder = orderRepository.findById(idOrderOld)
                                .orElseThrow(() -> new RuntimeException("Order not found"));
                        subOrder.setCancellationReason(cancellationReason);
                        subOrder.setStatusOrder(OrderStatus.Cancelled);
                        subOrder.getTableEntity().setCurrentOrderId(idOrderOld);
                        subOrder.getTableEntity().setStatus(TableStatus.OCCUPIED);
                        orderRepository.save(subOrder);
                }
                // Trả về thông tin đơn hàng sau khi hủy
                return ApiRespone.builder()
                        .result(orderMapper.toOrderResponeDTO(subOrder))
                        .build();
        }

}