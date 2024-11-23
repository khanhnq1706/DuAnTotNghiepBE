package com.example.demo.controller.payment;

import com.example.demo.repository.OrderDetailRepository;
import com.example.demo.respone.ApiRespone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/invoice")
public class InvoiceController {

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @GetMapping
    public ApiRespone<?> getInvoice(@RequestParam int idOrder) {

        return ApiRespone
                .builder()
                .result(orderDetailRepository.getInvoiceDetailsByOrderId(idOrder))
                .build();
    }

}
