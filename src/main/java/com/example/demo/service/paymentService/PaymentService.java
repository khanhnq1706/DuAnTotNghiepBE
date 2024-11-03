package com.example.demo.service.paymentService;

import com.example.demo.entity.OrderEntity;
import com.example.demo.repository.OrderRepository;
import com.example.demo.respone.VNPayResponseDTO;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PaymentService {

    @Autowired
    VNPayService  vnPayService;

    @Autowired
    OrderRepository orderRepository;


    public VNPayResponseDTO paymentByVNpay(int idOrder)  {
        OrderEntity orderNeedPayment = orderRepository
                .findById(idOrder)
                .orElseThrow(()-> new RuntimeException("Order_not_found"));
        try {
            return VNPayResponseDTO
                    .builder()
                    .urlToRedirect(vnPayService.payment(orderNeedPayment.getTotalNeedPayment(),String.valueOf(orderNeedPayment.getIdOrder()),null))
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
