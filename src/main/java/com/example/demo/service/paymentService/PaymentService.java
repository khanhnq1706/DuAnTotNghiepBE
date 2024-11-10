package com.example.demo.service.paymentService;

import com.example.demo.entity.OrderEntity;
import com.example.demo.entity.TableEntity;
import com.example.demo.enums.OrderStatus;
import com.example.demo.enums.TableStatus;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.TableRepository;
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

    @Autowired
    TableRepository tableRepository;

    public void paymentBycash(int idOrder){
        OrderEntity orderNeedPayment = orderRepository
                .findById(idOrder)
                .orElseThrow(()-> new RuntimeException("Order_not_found"));
        if(orderNeedPayment.getStatusOrder()== OrderStatus.Completed){
            throw new RuntimeException("Order_already_completed");
        }
        orderNeedPayment.setStatusOrder(OrderStatus.Completed);
        orderRepository.save(orderNeedPayment);
        TableEntity table = orderNeedPayment.getTableEntity();
        table.setStatus(TableStatus.AVAILABLE);
        table.setCurrentOrderId(null);
        tableRepository.save(table);
    }

    public VNPayResponseDTO paymentByVNpay(int idOrder)  {
        OrderEntity orderNeedPayment = orderRepository
                .findById(idOrder)
                .orElseThrow(()-> new RuntimeException("Order_not_found"));
        if(orderNeedPayment.getStatusOrder()== OrderStatus.Completed){
            throw new RuntimeException("Order_already_completed");
        }
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
