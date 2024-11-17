package com.example.demo.controller.admin.staff;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.enums.OrderStatus;
import com.example.demo.enums.TableStatus;
import com.example.demo.request.FoodRequestOrderDTO;
import com.example.demo.respone.ApiRespone;
import com.example.demo.respone.OrderResponeDTO;
import com.example.demo.respone.TableResponseDTO;
import com.example.demo.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/v1/order")
public class OrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    SimpMessagingTemplate messagingTemplate;

    @GetMapping("{id}")
    public ApiRespone<OrderResponeDTO> getOrder(@PathVariable("id") int id) {
        return orderService.getOrder(id);
    }

    @PostMapping
    public ApiRespone<OrderResponeDTO> confirmOrder(@RequestParam Integer idOrderOld,
            @RequestParam(required = false) Integer idOrderNew) {
        Integer idShift = getLoggedInStaffShift();
        if (idShift == null) {
            throw new RuntimeException("Nhân viên không có ca làm việc");
        }

        System.out.println("confirming");
        OrderResponeDTO orderResponeDTO = orderService.confirmOrder(idOrderOld, idOrderNew, idShift);
        messagingTemplate.convertAndSend("/topic/confirmorder", orderResponeDTO);
        System.out.println("confirm Oke");
        if (orderResponeDTO == null) {
            throw new RuntimeException("Không thể xác nhận đơn hàng!");
        }
        return ApiRespone.<OrderResponeDTO>builder()
                .result(orderResponeDTO)
                .build();

    }

    // Phương thức giả lập lấy idShift của nhân viên đang đăng nhập
    private Integer getLoggedInStaffShift() {
        // idShift của staff lấy ra tạm thời
        Integer idShift = 1;
        return idShift;
    }

    @PostMapping("create")
    public ApiRespone<?> createNewOrder(@RequestBody List<FoodRequestOrderDTO> listFoodOrder,
            @RequestParam Integer idTable, @RequestParam(required = false) String ipCustomer,
            @RequestParam(required = false) String numberPhone) {
        return ApiRespone.builder()
                .result(orderService.saveOrder(listFoodOrder, idTable, numberPhone, ipCustomer, OrderStatus.Preparing))
                .build();
    }

    @PutMapping("{idOrder}")
    public ApiRespone<?> updateOrder(@PathVariable Integer idOrder,
            @RequestBody FoodRequestOrderDTO listFoodOrder) {
        OrderResponeDTO updateorder = orderService.updateOrder(idOrder, listFoodOrder);
        return ApiRespone.builder().result(updateorder).build();
    }

    @PutMapping("{idOrder}/orderdetails/{idOrderDetail}")
    public ApiRespone<?> updateQuantity(@PathVariable("idOrder") int idOrder,
            @PathVariable("idOrderDetail") int idOrderDetail, @RequestBody int newQuantity) {
        OrderResponeDTO updatedOrder = orderService.updateQuantityOrderDetails(idOrder, idOrderDetail, newQuantity);
        return ApiRespone.builder().result(updatedOrder).build();
    }

    @DeleteMapping("/{idOrderDetail}")
    public ApiRespone<?> deleteOrderDetail(@PathVariable("idOrderDetail") int idOrderDetail) {
        return orderService.removeOrderdetail(idOrderDetail);
    }

    // @DeleteMapping("{idOld}/orderNew/{idNew}")
    // public ApiRespone<?> cancelOrder(@PathVariable(required = false) Integer
    // idNew,
    // @PathVariable(required = false) Integer idOld, @RequestBody String
    // cancellationReason) {
    // return ApiRespone.builder().result(orderService.cancelOrder(idOld, idNew,
    // cancellationReason))
    // .build();
    // }
    @PutMapping("cancel")
    public ApiRespone<?> cancelOrder(@RequestParam(required = false) Integer idOld,
            @RequestParam(required = false) Integer idNew, @RequestBody String cancellationReason) {
        return ApiRespone.builder().result(orderService.cancelOrder(idOld, idNew, cancellationReason))
                .build();
    }
}