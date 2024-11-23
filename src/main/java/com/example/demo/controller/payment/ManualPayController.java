package com.example.demo.controller.payment;

import com.example.demo.respone.ApiRespone;
import com.example.demo.service.paymentService.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/manual-Pay")
public class ManualPayController {

    @Autowired
    PaymentService paymentService;

    @PostMapping
    public ApiRespone<?> manualPay(@RequestParam int idOrder) {
        paymentService.paymentBycash(idOrder);
        return ApiRespone
                .builder()
                .build();
    }

}
