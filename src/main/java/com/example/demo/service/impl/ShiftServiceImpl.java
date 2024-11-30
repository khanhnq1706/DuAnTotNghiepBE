package com.example.demo.service.impl;

import com.example.demo.entity.OrderEntity;
import com.example.demo.entity.OrderWaitChangeShiftEntity;
import com.example.demo.entity.Shift;
import com.example.demo.entity.UserEnitty;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.OrderWaitChangeShiftRepository;
import com.example.demo.repository.ShiftRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.request.ShiftRequestDTO;
import com.example.demo.respone.InfoCheckoutShiftDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ShiftServiceImpl {

    @Autowired
    ShiftRepository shiftRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderWaitChangeShiftRepository orderWaitChangeShiftRepository;
    @Autowired
    private OrderRepository orderRepository;

    public Shift createShift(ShiftRequestDTO shiftRequestDTO) {
        UserEnitty user = userRepository.findById(shiftRequestDTO.getIdUser()).orElseThrow(() -> new RuntimeException("USER_NOT_EXISTS"));
        Shift shiftCheck = shiftRepository.findByIsWorking(true);
        if (shiftCheck != null) {
            throw new RuntimeException("another_shift_working");
        }
        Shift shift = Shift.
                builder()
                .cashAtStart(shiftRequestDTO.getCashAtStart())
                .userEnitty(user)
                .isWorking(true)
                .dateStart(LocalDateTime.now())
                .build();
        shiftRepository.save(shift);
        List<OrderWaitChangeShiftEntity> listWait = orderWaitChangeShiftRepository.findAll();
        for (OrderWaitChangeShiftEntity orderWait : listWait) {
            OrderEntity order = orderRepository.findById(orderWait.getOrderId()).get();
            order.setShift(null);
            order.setShift(shift);
            orderRepository.save(order);
        }
        orderWaitChangeShiftRepository.deleteAll();
        return shift;

    }

    public Shift endShift(ShiftRequestDTO shiftRequestDTO) {
        UserEnitty user = userRepository.findById(shiftRequestDTO.getIdUser()).orElseThrow(() -> new RuntimeException("USER_NOT_EXISTS"));
        Shift shift = shiftRepository.findByIsWorking(true);
        if (shift == null) {
            throw new RuntimeException("there_is_not_any_shift_working");
        }
        if (!shift.getUserEnitty().getIdUser().equals(shiftRequestDTO.getIdUser())) {
            throw new RuntimeException("NOT_YOUR_SHIFT");
        }

        List<OrderEntity> orders = shift.getOrders();
        double totalCash = 0;
        double totalEwallet = 0;
        double total = 0;
        for (OrderEntity order : orders) {

            switch (order.getNamePaymentMethod()) {
                case "cash":
                    totalCash += order.getTotal();
                    total += order.getTotal();
                    break;
                case "ewallet":
                    totalEwallet += order.getTotal();
                    total += order.getTotal();
                    break;
                default:
                    OrderWaitChangeShiftEntity orderWaitChangeShift = OrderWaitChangeShiftEntity
                            .builder()
                            .orderId(order.getIdOrder())
                            .build();
                    orderWaitChangeShiftRepository.save(orderWaitChangeShift);
            }


        }
        if (totalCash + shift.getCashAtStart() != shiftRequestDTO.getCashAmountEnd()) {
            throw new RuntimeException("wrong_cash_checkout");
        }
        shift.setDateEnd(LocalDateTime.now());
        shift.setWorking(false);
        shift.setCashAmountEnd(totalCash);
        shift.setBankAmountEnd(totalEwallet);
        shift.setShiftRevenue(total);
        return shiftRepository.save(shift);

    }


    public Shift endDay(ShiftRequestDTO shiftRequestDTO) {
        UserEnitty user = userRepository.findById(shiftRequestDTO.getIdUser()).orElseThrow(() -> new RuntimeException("USER_NOT_EXISTS"));
        Shift shift = shiftRepository.findByIsWorking(true);
        if (shift == null) {
            throw new RuntimeException("there_is_not_any_shift_working");
        }
        if (!shift.getUserEnitty().getIdUser().equals(shiftRequestDTO.getIdUser())) {
            throw new RuntimeException("NOT_YOUR_SHIFT");
        }

        List<OrderEntity> orders = shift.getOrders();
        double totalCash = 0;
        double totalEwallet = 0;
        double total = 0;
        for (OrderEntity order : orders) {

            switch (order.getNamePaymentMethod()) {
                case "cash":
                    totalCash += order.getTotal();
                    total += order.getTotal();
                    break;
                case "ewallet":
                    totalEwallet += order.getTotal();
                    total += order.getTotal();
                    break;
                default:
                    throw new RuntimeException("Have_Order_Serving");
            }


        }
        if (totalCash + shift.getCashAtStart() != shiftRequestDTO.getCashAmountEnd()) {
            throw new RuntimeException("wrong_cash_checkout");
        }
        shift.setDateEnd(LocalDateTime.now());
        shift.setWorking(false);
        shift.setCashAmountEnd(totalCash);
        shift.setBankAmountEnd(totalEwallet);
        shift.setShiftRevenue(total);
        return shiftRepository.save(shift);

    }

    public void validShift(UUID idStaff) {
        Shift shiftCheck = shiftRepository.findByIsWorking(true);
        if (shiftCheck != null && !shiftCheck.getUserEnitty().getIdUser().equals(idStaff)) {
            throw new RuntimeException("another_shift_working");
        }
        if (shiftCheck == null) {
            throw new RuntimeException("there_is_not_any_shift_working");
        }
    }

    public InfoCheckoutShiftDTO getInfoCheckoutShift() {
        Shift shift = shiftRepository.findByIsWorking(true);
        if (shift == null) {
            throw new RuntimeException("there_is_not_any_shift_working");
        }

        List<OrderEntity> orders = shift.getOrders();
        double totalCash = 0;
        double totalEwallet = 0;
        double total = 0;
        double totalServing = 0;
        for (OrderEntity order : orders) {

            switch (order.getNamePaymentMethod()) {
                case "cash":
                    totalCash += order.getTotal();
                    total += order.getTotal();
                    break;
                case "ewallet":
                    totalEwallet += order.getTotal();
                    total += order.getTotal();
                    break;
                default:
                    totalServing += order.getTotal();
                    total += order.getTotal();
                    break;

            }

        }

        return InfoCheckoutShiftDTO
                .builder()
                .shiftRevenue(total)
                .totalServing(totalServing)
                .bankAmountEnd(totalEwallet)
                .cashAmountEnd(totalCash)
                .cashAtStart(shift.getCashAtStart())
                .DateStart(shift.getDateStart())
                .build();
    }

}
