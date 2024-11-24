package com.example.demo.controller.admin.staff;

import com.example.demo.entity.Shift;
import com.example.demo.request.ShiftRequestDTO;
import com.example.demo.respone.ApiRespone;
import com.example.demo.service.impl.ShiftServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/shift")
public class ShiftController {

    @Autowired
    ShiftServiceImpl shiftService;

    @PostMapping("create")
    public ApiRespone<?> createShift(@RequestBody ShiftRequestDTO shift) {
        System.out.println(shift.toString());
        return ApiRespone
                .builder()
                .result(shiftService.createShift(shift))
                .build();
    }

    @PostMapping("end")
    public ApiRespone<?> endShift(@RequestBody ShiftRequestDTO shift) {
        System.out.println(shift.toString());
        return ApiRespone
                .builder()
                .result(shiftService.endShift(shift))
                .build();
    }

    @PostMapping("endDay")
    public ApiRespone<?> endDayShift(@RequestBody ShiftRequestDTO shift) {
        System.out.println(shift.toString());
        return ApiRespone
                .builder()
                .result(shiftService.endDay(shift))
                .build();
    }

    @PostMapping("valid")
    public ApiRespone<?> validShift(@RequestParam UUID idUser) {
        shiftService.validShift(idUser);
        return ApiRespone
                .builder()
                .build();
    }

    @GetMapping("infoCheckout")
    public ApiRespone<?> infoCheckoutShift() {
        return ApiRespone
                .builder()
                .result(shiftService.getInfoCheckoutShift())
                .build();
    }
}
