package com.example.demo.service;

import com.example.demo.enums.OrderStatus;
import com.example.demo.enums.TableStatus;
import com.example.demo.request.FoodRequestOrderDTO;
import com.example.demo.respone.ApiRespone;
import com.example.demo.respone.OrderResponeDTO;
import com.example.demo.respone.TableResponseDTO;

import java.util.List;

public interface OrderService {
    ApiRespone<OrderResponeDTO> getOrder(int idOrder);

    OrderResponeDTO saveOrder(List<FoodRequestOrderDTO> listFoodOrder, Integer idTable, String numbePhone,
            String ipCustomer,
            OrderStatus status);

    OrderResponeDTO confirmOrder(Integer idOrderOld, Integer idOrderNew, Integer idShift);

    OrderResponeDTO updateOrder(Integer idOrder, FoodRequestOrderDTO foodOrder);

    OrderResponeDTO updateQuantityOrderDetails(int idOrder, int idOrderdetail, int newQuantity);

    ApiRespone<?> removeOrderdetail(int idOrderDetail);

    ApiRespone<?> cancelOrder(Integer idOrderOld, Integer idOrderNew, String cancellationReason);
}
