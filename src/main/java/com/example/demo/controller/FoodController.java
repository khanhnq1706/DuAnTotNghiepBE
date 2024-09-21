package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.repository.FoodFindRepository;

@RestController
@RequestMapping("/api/foodEntities")
@CrossOrigin("http://localhost:4200")
public class FoodController {
	@Autowired
	private FoodFindRepository foodFindRepository;

}
