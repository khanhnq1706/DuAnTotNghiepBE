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

import org.hibernate.sql.ast.tree.expression.Over;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        public Page<OrderEntity> filterOrders(OrderStatus statusOrder, Integer idOrder, Date dateFrom, Date dateTo,
                        String searchKeyword, int page, int size) {
                Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC,
                                "idOrder"));
                return orderRepository.filterOrders(statusOrder, idOrder, dateFrom, dateTo, searchKeyword, pageable);
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
        public OrderResponeDTO confirmOrder(Integer idOrder, Integer idShift) {
                OrderEntity suborder = orderRepository.findById(idOrder)
                                .orElseThrow(() -> new RuntimeException("Order_not_exist"));
                TableEntity tableOrder = suborder.getTableEntity();
                Shift shift = shiftRepository
                                .findById(idShift)
                                .orElseThrow(() -> new RuntimeException("Shift_not_exist"));
                if (tableOrder.getIdOrderMain() != null && idOrder != null) {

                        OrderEntity mainOrder = orderRepository
                                        .findById(tableOrder.getIdOrderMain())
                                        .orElseThrow(() -> new RuntimeException("IdOrderMain_not_exist"));
                        mergeOrderDetails(mainOrder, suborder);
                        mainOrder.setStatusOrder(OrderStatus.Preparing);
                        suborder.setStatusOrder(OrderStatus.Merged);
                        tableOrder.setStatus(TableStatus.OCCUPIED);
                        mainOrder.setTotal(suborder.getTotal() + mainOrder.getTotal());
                        orderRepository.save(mainOrder);
                        tableOrder.setCurrentOrderId(mainOrder.getIdOrder());
                } else {
                        System.out.println("yepppp");
                        suborder.setShift(shift);
                        suborder.setStatusOrder(OrderStatus.Preparing);
                        tableOrder.setStatus(TableStatus.OCCUPIED);
                        tableOrder.setIdOrderMain(idOrder);
                        orderRepository.save(suborder);
                }
                tableRepository.save(tableOrder);
                return orderMapper.toOrderResponeDTO(suborder);
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
        public ApiRespone<?> cancelOrder(Integer idOrder, String cancellationReason) {
                OrderEntity subOrder = orderRepository.findById(idOrder)
                                .orElseThrow(() -> new RuntimeException("IdOrder_not_found"));

                TableEntity tableOrder = subOrder.getTableEntity();
                if (tableOrder.getIdOrderMain() != null) {
                        // Bàn có IdOrderMain
                        OrderEntity mainOrder = orderRepository.findById(tableOrder.getIdOrderMain())
                                        .orElseThrow(() -> new RuntimeException("IdOrderMain_not_exist"));

                        if (idOrder != tableOrder.getIdOrderMain() && !idOrder.equals(tableOrder.getIdOrderMain())) {
                                System.out.println("idorder=o");
                                System.out.println(tableOrder.getIdOrderMain());
                                System.out.println(idOrder);
                                // Trường hợp hủy gộp đơn
                                subOrder.setStatusOrder(OrderStatus.Cancelled);
                                subOrder.setCancellationReason(cancellationReason);

                                tableOrder.setCurrentOrderId(tableOrder.getIdOrderMain());
                                tableOrder.setStatus(TableStatus.OCCUPIED);
                                orderRepository.save(mainOrder);
                                orderRepository.save(subOrder);
                        } else {
                                System.out.println("Donchinhvadonhientai");
                                // Hủy cả đơn chính và đơn hiện tại
                                subOrder.setStatusOrder(OrderStatus.Cancelled);
                                subOrder.setCancellationReason(cancellationReason);

                                mainOrder.setStatusOrder(OrderStatus.Cancelled);
                                mainOrder.setCancellationReason(cancellationReason);
                                // Thiết lập bàn
                                tableOrder.setCurrentOrderId(null);
                                tableOrder.setIdOrderMain(null);
                                tableOrder.setStatus(TableStatus.AVAILABLE);
                                tableOrder.setCurrentIP(null);

                                orderRepository.save(subOrder);
                                orderRepository.save(mainOrder);
                        }
                } else {
                        // bàn không có IdOrderMain
                        subOrder.setStatusOrder(OrderStatus.Cancelled);
                        subOrder.setCancellationReason(cancellationReason);

                        tableOrder.setCurrentOrderId(null);
                        tableOrder.setIdOrderMain(null);
                        tableOrder.setStatus(TableStatus.AVAILABLE);
                        tableOrder.setCurrentIP(null);
                        orderRepository.save(subOrder);
                }

                tableRepository.save(tableOrder);

                return ApiRespone.builder()
                                .result(orderMapper.toOrderResponeDTO(subOrder))
                                .build();
        }

}