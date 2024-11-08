package com.example.demo.controller.admin.staff;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.OrderDetailEntity;
import com.example.demo.entity.OrderEntity;
import com.example.demo.entity.TableEntity;
import com.example.demo.repository.OrderDetailRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.TableRepository;
import com.example.demo.respone.ApiRespone;
import com.example.demo.respone.OrderDetailResponeDTO;
import com.example.demo.service.OrderDetailService;

@RestController
@RequestMapping("api/v1/orderdetail")
public class OrderDetailStaffController {
    @Autowired
    OrderDetailService orderDetailService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    TableRepository tableRepository;

    @GetMapping
    public ApiRespone<List<OrderDetailResponeDTO>> getOrderDetails(@RequestParam(required = false) Integer idOrder,
            @RequestParam(required = false) Integer idTable) {
        List<OrderDetailResponeDTO> orderDetailDTOs = orderDetailService.getOrderDetails(idOrder, idTable);

        if (orderDetailDTOs.isEmpty()) {
            return ApiRespone.<List<OrderDetailResponeDTO>>builder()
                    .code(1000) // Mã phù hợp với tình huống không có dữ liệu
                    .message("No data found")
                    .result(Collections.emptyList())
                    .build();
        }

        return ApiRespone.<List<OrderDetailResponeDTO>>builder()
                .result(orderDetailDTOs)
                .build();
    }

}
