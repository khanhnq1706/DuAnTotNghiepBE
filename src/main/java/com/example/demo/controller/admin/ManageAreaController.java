package com.example.demo.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.respone.ApiRespone;
import com.example.demo.service.AreaService;
import com.example.demo.service.CategoryService;

@RestController
@RequestMapping("api/v1/areas")
public class ManageAreaController {
    @Autowired
    private AreaService areaService;

    @GetMapping
    public ApiRespone<?> getAllAreas() {
        return ApiRespone.builder()
                .result(areaService.findAll())
                .build();
    }
}
