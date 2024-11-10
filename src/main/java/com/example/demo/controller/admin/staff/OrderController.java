package com.example.demo.controller.admin.staff;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.respone.ApiRespone;
import com.example.demo.respone.OrderResponeDTO;
import com.example.demo.respone.TableResponseDTO;
import com.example.demo.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
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
    public ApiRespone<OrderResponeDTO> confirmOrder(@RequestParam Integer idOrder, @RequestParam Integer idTable) {
        Integer idShift = getLoggedInStaffShift();
        if (idShift == null) {
            throw new RuntimeException("Nhân viên không có ca làm việc");
        }

        System.out.println("confirming");
        OrderResponeDTO orderResponeDTO = orderService.confirmOrder(idOrder, idShift, idTable);
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

}
