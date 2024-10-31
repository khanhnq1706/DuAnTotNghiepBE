package com.example.demo.service;

import com.example.demo.request.FoodRequestOrderDTO;
import com.example.demo.respone.OrderResponeDTO;

import java.util.List;

public interface OrderService {
    OrderResponeDTO saveOrder(List<FoodRequestOrderDTO> listFoodOrder, Integer idTable, String numbePhone);
}
