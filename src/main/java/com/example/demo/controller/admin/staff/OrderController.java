package com.example.demo.controller.admin.staff;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.example.demo.entity.Shift;
import com.example.demo.repository.ShiftRepository;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
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

import com.example.demo.entity.OrderEntity;
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
    @Autowired
    private ShiftRepository shiftRepository;

    @GetMapping("{id}")
    public ApiRespone<OrderResponeDTO> getOrder(@PathVariable("id") int id) {
        return orderService.getOrder(id);
    }

    @GetMapping("/filter")
    public ResponseEntity<ApiRespone<?>> filterOrders(
            @RequestParam(required = false) OrderStatus statusOrder,
            @RequestParam(required = false) Integer idOrder,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dateFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dateTo,
            @RequestParam(required = false) String searchKeyword,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "8") int size) {
        Page<OrderEntity> filteredOrders = orderService.filterOrders(statusOrder, idOrder, dateFrom,
                dateTo, searchKeyword, page, size);
        return ResponseEntity.ok(ApiRespone.builder()
                .result(filteredOrders)
                .build());
    }

    @PostMapping
    public ApiRespone<OrderResponeDTO> confirmOrder(@RequestParam(required = false) Integer idOrder) {
        Shift shift = shiftRepository
                .findByIsWorking(true);
        if (shift == null) {
            throw new RuntimeException("Shift_not_exist");
        }

        OrderResponeDTO orderResponeDTO = orderService.confirmOrder(idOrder, shift.getIdShift());
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
    public ApiRespone<?> cancelOrder(@RequestParam Integer idOrder,
            @RequestBody String cancellationReason) {
        return orderService.cancelOrder(idOrder, cancellationReason);
    }
}