package com.example.demo.service;

import com.example.demo.entity.OrderEntity;
import com.example.demo.enums.OrderStatus;
import com.example.demo.enums.TableStatus;
import com.example.demo.request.FoodRequestOrderDTO;
import com.example.demo.respone.ApiRespone;
import com.example.demo.respone.OrderResponeDTO;
import com.example.demo.respone.TableResponseDTO;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;

public interface OrderService {

        Page<OrderEntity> filterOrders(OrderStatus statusOrder, Integer idOrder, Date dateFrom, Date dateTo,
                        String searchKeyword,
                        int page, int size);

        ApiRespone<OrderResponeDTO> getOrder(int idOrder);

        OrderResponeDTO saveOrder(List<FoodRequestOrderDTO> listFoodOrder, Integer idTable, String numbePhone,
                        String ipCustomer,
                        OrderStatus status);

        OrderResponeDTO confirmOrder(Integer idOrder, Integer idShift);

        OrderResponeDTO updateOrder(Integer idOrder, FoodRequestOrderDTO foodOrder);

        OrderResponeDTO updateQuantityOrderDetails(int idOrder, int idOrderdetail, int newQuantity);

        ApiRespone<?> removeOrderdetail(int idOrderDetail);

        ApiRespone<?> cancelOrder(Integer idOrder, String cancellationReason);
}
