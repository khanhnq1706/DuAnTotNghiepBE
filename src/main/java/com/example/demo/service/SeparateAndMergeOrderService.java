package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.FoodEntity;
import com.example.demo.entity.OrderDetailEntity;
import com.example.demo.entity.OrderEntity;
import com.example.demo.entity.TableEntity;
import com.example.demo.enums.TableStatus;
import com.example.demo.request.FoodRequestOrderDTO;
import com.example.demo.respone.OrderResponeDTO;

import jakarta.transaction.Transactional;

public interface SeparateAndMergeOrderService {
	
	OrderResponeDTO updateOrderAll(Integer idOrder, List<FoodRequestOrderDTO> requestOrderDTO);

	void deleteOrder(Integer idOrder);
    
}
