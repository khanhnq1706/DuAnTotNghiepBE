package com.example.demo.service;

import java.util.List;

import com.example.demo.respone.ApiRespone;
import com.example.demo.respone.OrderDetailResponeDTO;

public interface OrderDetailService {
    List<OrderDetailResponeDTO> getOrderDetails(Integer idOrder, Integer idTable);
}
