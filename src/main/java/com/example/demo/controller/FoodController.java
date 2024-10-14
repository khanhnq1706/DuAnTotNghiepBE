package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller.admin.ManageCategoryController;
import com.example.demo.entity.CategoryFoodEntity;
import com.example.demo.entity.FoodEntity;
import com.example.demo.repository.FoodFindRepository;
import com.example.demo.request.FoodRequestDTO;
import com.example.demo.respone.ApiRespone;
import com.example.demo.respone.CategoryRespone;
import com.example.demo.respone.FoodResponeDTO;
import com.example.demo.service.FoodService;
import com.example.demo.service.impl.CategoryServiceImpl;
import com.example.demo.service.impl.FoodServiceImpl;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/v1/foodEntities")
//@CrossOrigin("http://localhost:4200")
public class FoodController {
	@Autowired
	private FoodFindRepository foodFindRepository;
	@Autowired
	private FoodService foodService;
	@Autowired
	private FoodServiceImpl foodServiceImpl;
	@Autowired
	ManageCategoryController categoryController;
	@Autowired
	private CategoryServiceImpl categoryServiceImpl;

	
    @GetMapping("filter")
    public ApiRespone<?>getFoodFromFilter(@RequestParam(required = false) String  nameFood,
    		@RequestParam(required = false) String idCategory,
    		@RequestParam(value = "page", defaultValue = "0") int page,
    	    @RequestParam(value = "size", defaultValue = "10") int size){
    	Pageable pageable = PageRequest.of(page, size);
    	  return ApiRespone.builder()
                  .result(foodService.getFoodFromFilter(nameFood,idCategory,pageable))
                  .build();
    }
}
