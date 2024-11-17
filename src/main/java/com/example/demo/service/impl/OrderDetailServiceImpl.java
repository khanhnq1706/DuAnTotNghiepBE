package com.example.demo.service.impl;

import com.example.demo.entity.OrderDetailEntity;
import com.example.demo.entity.OrderEntity;
import com.example.demo.entity.TableEntity;
import com.example.demo.map.OrderdetailMapper;
import com.example.demo.repository.OrderDetailRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.TableRepository;
import com.example.demo.respone.ApiRespone;
import com.example.demo.respone.OrderDetailResponeDTO;
import com.example.demo.service.OrderDetailService;
import com.example.demo.service.TableService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    TableRepository tableRepository;

    @Autowired
    OrderdetailMapper orderdetailMapper;

    @Override
    public List<OrderDetailResponeDTO> getOrderDetails(Integer idOrder, Integer idTable) {
        List<OrderDetailEntity> orderDetails;

        // Kiểm tra idOrder và lấy dữ liệu tương ứng
        if (idOrder != null) {
            OrderEntity orderEntity = orderRepository.findById(idOrder)
                    .orElseThrow(() -> new RuntimeException("IdOrder_not_found"));
            orderDetails = orderDetailRepository.findByOrderEntity(orderEntity);
        } else if (idTable != null) {
            TableEntity tableEntity = tableRepository.findById(idTable)
                    .orElseThrow(() -> new RuntimeException("Table_not_found"));
            orderDetails = orderDetailRepository.findByOrderEntity_TableEntity(tableEntity);
        } else {
            return Collections.emptyList();
        }

        return orderDetails.stream()
                .map(orderdetailMapper::toOrderDetailResponeDTO)
                .collect(Collectors.toList());
    }
    
}
