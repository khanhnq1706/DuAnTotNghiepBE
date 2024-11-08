package com.example.demo.service;

import com.example.demo.request.FoodRequestOrderDTO;
import com.example.demo.respone.ApiRespone;
import com.example.demo.respone.OrderResponeDTO;
import com.example.demo.respone.TableResponseDTO;

import java.util.List;

public interface OrderService {
    OrderResponeDTO saveOrder(List<FoodRequestOrderDTO> listFoodOrder, Integer idTable, String numbePhone);

    ApiRespone<OrderResponeDTO> getOrder(int idOrder);

    OrderResponeDTO confirmOrder(Integer idOrder, Integer idShift, Integer idTable);

}
