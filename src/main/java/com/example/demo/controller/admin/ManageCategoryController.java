package com.example.demo.controller.admin;

import com.example.demo.entity.CategoryFoodEntity;
import com.example.demo.respone.ApiRespone;
import com.example.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/categories")
public class ManageCategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ApiRespone<?> getAllCategories() {
        return ApiRespone.builder()
                .result(categoryService.findAll())
                .build();
    }

}
