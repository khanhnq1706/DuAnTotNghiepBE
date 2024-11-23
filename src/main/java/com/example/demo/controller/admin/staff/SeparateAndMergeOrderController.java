package com.example.demo.controller.admin.staff;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.request.FoodRequestOrderDTO;
import com.example.demo.respone.ApiRespone;
import com.example.demo.service.OrderService;
import com.example.demo.service.SeparateAndMergeOrderService;

@RestController
@RequestMapping("api/v1/separateandmergeorder")
public class SeparateAndMergeOrderController {
	 @Autowired
	    private SeparateAndMergeOrderService separateAndMergeOrderService;
	 
	    @Autowired
	    SimpMessagingTemplate messagingTemplate;
	    
	    
	    @PutMapping("/update/{idOrder}")
	    public ApiRespone<?> updateOrder1(@PathVariable Integer idOrder,
	                                     @RequestBody List<FoodRequestOrderDTO> requestOrderDTO) {
	        var result = separateAndMergeOrderService.updateOrderAll(idOrder, requestOrderDTO);
	        messagingTemplate.convertAndSend("/topic/updateorder", result);
	        return ApiRespone.builder()
	                .result(result)
	                .build();
	    }
	    @DeleteMapping("/delete/{idOrder}")
	    public ResponseEntity<?> deleteOrderById(@PathVariable Integer idOrder) {
	        try {
	        	separateAndMergeOrderService.deleteOrder(idOrder);
	            return ResponseEntity.noContent().build();
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR) 
	                    .body(Map.of(
	                        "error", "Internal Server Error",
	                        "message", e.getMessage(),
	                        "idOrder", idOrder
	                    ));
	        }
	    }
}
