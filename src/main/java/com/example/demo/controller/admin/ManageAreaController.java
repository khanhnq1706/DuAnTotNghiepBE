package com.example.demo.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.request.AreaRequestDTO;
import com.example.demo.request.TableRequestDTO;
import com.example.demo.respone.ApiRespone;
import com.example.demo.respone.AreaResponeDTO;
import com.example.demo.respone.TableResponseDTO;
import com.example.demo.service.AreaService;
import com.example.demo.service.CategoryService;

import jakarta.validation.Valid;
import lombok.Delegate;
import org.springframework.web.bind.annotation.PutMapping;

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

    @GetMapping("{id}")
    public ApiRespone<AreaResponeDTO> getTable(@PathVariable("id") int idarea) {
        return areaService.getArea(idarea);
    }

    @PostMapping
    public ApiRespone<?> createArea(@Valid @RequestBody AreaRequestDTO request) {
        return ApiRespone.builder().result(areaService.createArea(request)).build();
    }

    @DeleteMapping("{id}")
    public ApiRespone<?> deleteTable(@PathVariable("id") int idarea) {
        return areaService.deleteArea(idarea);
    }

    @PutMapping("{id}")
    public ApiRespone<?> putArea(@Valid @RequestBody AreaRequestDTO request,
            @PathVariable("id") int idArea) {
        return ApiRespone.builder().result(areaService.updateArea(request, idArea)).build();
    }

}
