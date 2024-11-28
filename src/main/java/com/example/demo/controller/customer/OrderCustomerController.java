package com.example.demo.controller.customer;

import com.example.demo.respone.ApiRespone;
import com.example.demo.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/orderCustomer")
public class OrderCustomerController {

    @Autowired
    OrderServiceImpl orderService;

    @PostMapping ("listIdOrder")
    public ApiRespone<?> getAllOrderByListId(@RequestBody List<Integer> listId) {
        System.out.println("Received numbers: " + listId);
        return ApiRespone
                .builder()
                .result(orderService.getListOrderByListId(listId))
                .build();
    }
}
