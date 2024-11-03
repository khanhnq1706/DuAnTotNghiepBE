package com.example.demo.controller.payment;

import com.example.demo.config.vnpayConfig.Config;
import com.example.demo.respone.ApiRespone;
import com.example.demo.respone.VNPayResponseDTO;
import com.example.demo.service.paymentService.PaymentService;
import com.example.demo.service.paymentService.VNPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("api/payment-VNPay")
public class VNPayController {
    @Autowired
    private PaymentService  paymentService;

    @Autowired
    private VNPayService  vnPayService;

    @Value("${host.fe}")
    private String hostFE;

    @PostMapping
    public ApiRespone<?> postRequestCallPayment(@RequestParam int idOrder) {

        return ApiRespone
                .builder()
                .result(paymentService.paymentByVNpay(idOrder))
                .build();
    }
    @GetMapping
    public RedirectView vnpayReturn(){
        VNPayResponseDTO response = vnPayService.vnpayReturn();
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(hostFE);
        redirectView.addStaticAttribute("RspCode",response.getRspCode());
        return redirectView;
    }
}
