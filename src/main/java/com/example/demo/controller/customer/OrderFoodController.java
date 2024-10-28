package com.example.demo.controller.customer;

import com.example.demo.request.FoodRequestOrderDTO;
import com.example.demo.respone.ApiRespone;
import com.example.demo.respone.OrderResponeDTO;
import com.example.demo.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/order")

public class OrderFoodController {

    @Autowired
    OrderService orderService;
    @PostMapping
    public ApiRespone<?> postOrder(@RequestBody List<FoodRequestOrderDTO> listFoodOrder,
                                   @RequestParam Integer idTable,@RequestParam(required = false) String numberPhone) {
        System.out.println(listFoodOrder.toString());
        System.out.println("id table :"+idTable);
        return ApiRespone.builder()
                .result(orderService.saveOrder(listFoodOrder, idTable, numberPhone))
                .build();
    }

}
