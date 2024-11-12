package com.example.demo.controller.customer;

import com.example.demo.enums.OrderStatus;
import com.example.demo.enums.TableStatus;
import com.example.demo.request.FoodRequestOrderDTO;
import com.example.demo.respone.ApiRespone;
import com.example.demo.respone.OrderResponeDTO;
import com.example.demo.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/order")

public class OrderFoodController {

    @Autowired
    OrderService orderService;
    @Autowired
    SimpMessagingTemplate messagingTemplate;

    // @MessageMapping
    // @SendTo("/topic/postorder")
    @PostMapping
    public ApiRespone<?> postOrder(@RequestBody List<FoodRequestOrderDTO> listFoodOrder,
            @RequestParam Integer idTable, @RequestParam(required = false) String numberPhone) {
        var result = orderService.saveOrder(listFoodOrder, idTable, numberPhone, OrderStatus.Waiting);
        messagingTemplate.convertAndSend("/topic/postorder", result);
        System.out.println(listFoodOrder.toString());
        System.out.println("id table :" + idTable);
        return ApiRespone.builder()
                .result(result)
                .build();
    }
}
