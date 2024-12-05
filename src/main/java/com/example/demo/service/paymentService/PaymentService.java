package com.example.demo.service.paymentService;

import com.example.demo.entity.OrderEntity;
import com.example.demo.entity.PromotionEntity;
import com.example.demo.entity.TableEntity;
import com.example.demo.enums.OrderStatus;
import com.example.demo.enums.PaymentMethod;
import com.example.demo.enums.TableStatus;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.PromotionRepository;
import com.example.demo.repository.TableRepository;
import com.example.demo.respone.VNPayResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    VNPayService vnPayService;

    @Autowired
    OrderRepository orderRepository;
	@Autowired
	PromotionRepository promotionRepository;
    @Autowired
    TableRepository tableRepository;

    public void paymentBycash(int idOrder , int idPromotion) {
    	
        OrderEntity orderNeedPayment = orderRepository
                .findById(idOrder)
                .orElseThrow(() -> new RuntimeException("Order_not_found"));
     
        if (orderNeedPayment.getStatusOrder() == OrderStatus.Completed) {
            throw new RuntimeException("Order_already_completed");
        }
        PromotionEntity promotionEntity = promotionRepository.findByIdPromotion(idPromotion);
        if (promotionEntity == null) {
        	orderNeedPayment.setPromotionEntity(null); 
            orderNeedPayment.setNamePaymentMethod(PaymentMethod.Cash.getName());
            orderNeedPayment.setStatusOrder(OrderStatus.Completed);
            orderNeedPayment.setPaymentDate(new Date());
            orderRepository.save(orderNeedPayment);
            TableEntity table = orderNeedPayment.getTableEntity();
            table.setStatus(TableStatus.AVAILABLE);
            table.setCurrentOrderId(null);
            table.setIdOrderMain(null);
            table.setCurrentIP(null);
            tableRepository.save(table);
        } else {
        	 if(promotionEntity.isIncreasePrice()) {
      		   orderNeedPayment.setTotal(orderNeedPayment.getTotal()+(orderNeedPayment.getTotal() *promotionEntity.getDiscount() / 100));
      	   
      	  
         }else if (!promotionEntity.isIncreasePrice()) {
      	   orderNeedPayment.setTotal(orderNeedPayment.getTotal()-(orderNeedPayment.getTotal() *promotionEntity.getDiscount() / 100));
         }
         
          orderNeedPayment.setPromotionEntity(promotionEntity);   
          orderNeedPayment.setNamePaymentMethod(PaymentMethod.Cash.getName());
          orderNeedPayment.setStatusOrder(OrderStatus.Completed);
          orderNeedPayment.setPaymentDate(new Date());
          orderRepository.save(orderNeedPayment);
          TableEntity table = orderNeedPayment.getTableEntity();
          table.setStatus(TableStatus.AVAILABLE);
          table.setCurrentOrderId(null);
          table.setIdOrderMain(null);
          table.setCurrentIP(null);
          tableRepository.save(table);
        }
      
    }

    public VNPayResponseDTO paymentByVNpay(int idOrder, int idPromotion) {
        OrderEntity orderNeedPayment = orderRepository
                .findById(idOrder)
                .orElseThrow(() -> new RuntimeException("Order_not_found"));
        if (orderNeedPayment.getStatusOrder() == OrderStatus.Completed) {
            throw new RuntimeException("Order_already_completed");
        }
      
        PromotionEntity promotionEntity = promotionRepository.findByIdPromotion(idPromotion);
        try {
        	 if (promotionEntity != null) {
        		 orderNeedPayment.setPromotionEntity(promotionEntity); 
        	 
        		 if(promotionEntity.isIncreasePrice()) {
            		   orderNeedPayment.setTotal(orderNeedPayment.getTotal()+(orderNeedPayment.getTotal() *promotionEntity.getDiscount() / 100));
            	
               }else if (!promotionEntity.isIncreasePrice()) {
            	   orderNeedPayment.setTotal(orderNeedPayment.getTotal()-(orderNeedPayment.getTotal() *promotionEntity.getDiscount() / 100));
               }
        	 }
        	orderRepository.save(orderNeedPayment);
            return VNPayResponseDTO
                    .builder()
                    .urlToRedirect(vnPayService.payment(orderNeedPayment.getTotalNeedPayment(),
                            String.valueOf(orderNeedPayment.getIdOrder()), null))
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
