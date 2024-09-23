package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.CategoryFoodEntity;
import com.example.demo.entity.FoodEntity;
import com.example.demo.repository.FoodFindRepository;
import com.example.demo.request.FoodRequestDTO;
import com.example.demo.respone.FoodResponeDTO;
import com.example.demo.service.impl.CategoryServiceImpl;
import com.example.demo.service.impl.FoodServiceImpl;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("/api/foodEntities")
@CrossOrigin("http://localhost:4200")
public class FoodController {
	@Autowired
	private FoodFindRepository foodFindRepository;
	
	@Autowired
	private FoodServiceImpl foodServiceImpl;
	
	@Autowired
	private CategoryServiceImpl categoryServiceImpl;
	
	@GetMapping("")
	public List<FoodEntity> getListFood() {
		return foodFindRepository.findAll();
	}
	
	
	@GetMapping("{id}")
	public List<FoodEntity> findByIdCategory(@PathVariable int id) {
		CategoryFoodEntity categoryFoodEntity = categoryServiceImpl.findById(id).orElse(null);
		return foodServiceImpl.findByCategory(categoryFoodEntity);
	}
	
	
	
}
