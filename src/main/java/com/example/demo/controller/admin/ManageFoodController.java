package com.example.demo.controller.admin;

import com.example.demo.request.FoodRequestDTO;
import com.example.demo.respone.ApiRespone;
import com.example.demo.respone.FoodResponeDTO;
import com.example.demo.service.FoodService;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/foods")
public class ManageFoodController {

    @Autowired
    private FoodService foodService;

    @GetMapping
    public ApiRespone<?> getAllFoods() {
        return ApiRespone.builder()
                .result(foodService.getAllFood())
                .build();
    }

    @GetMapping("{idfood}")
    public ApiRespone<?> getFoodById(@PathVariable("idfood") int idFood) {
        return ApiRespone.builder()
                .result(foodService.getFoodById(idFood))
                .build();
    }

    @PostMapping
    public ApiRespone<?> postFood(@ModelAttribute @Valid FoodRequestDTO requestDTO,
            @RequestParam(name = "file", required = false) MultipartFile file) {
        System.out.println(requestDTO.toString());
        return ApiRespone.builder()
                .result(foodService.saveFood(requestDTO, file))
                .build();

    }

    @PutMapping("{id}")
    public ApiRespone<?> putFood(@PathVariable("id") int idFood, @ModelAttribute @Valid FoodRequestDTO requestDTO,
            @RequestParam(name = "file", required = false) MultipartFile file) {
        // System.out.println("testing here :"+requestDTO.toString());
        return ApiRespone.builder()
                .result(foodService.updateFood(idFood, requestDTO, file))
                .build();
    }

    @GetMapping("filter")
    public ApiRespone<?> getFoodFromFilter(@RequestParam(required = false) String nameFood,
            @RequestParam(required = false) String idCategory,
            @RequestParam(required = false) String isSelling,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        System.out.println(page);
        System.out.println(size);
        Pageable pageable = PageRequest.of(page, size);
        return ApiRespone.builder()
                .result(foodService.getFoodFromFilter(nameFood, idCategory, isSelling, pageable))
                .build();
    }

    @GetMapping("category/{idCategory}")
    public ApiRespone<List<FoodResponeDTO>> getFoodByIdCategory(@PathVariable("idCategory") Integer idCategory) {
        return ApiRespone.<List<FoodResponeDTO>>builder().result(foodService.getFoodByIdCategory(idCategory)).build();
    }
}
