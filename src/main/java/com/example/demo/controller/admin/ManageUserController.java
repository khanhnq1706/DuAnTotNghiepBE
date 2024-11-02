package com.example.demo.controller.admin;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.request.FoodRequestDTO;
import com.example.demo.request.UserRequestDTO;
import com.example.demo.respone.ApiRespone;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("api/v1/users")
public class ManageUserController {
    @Autowired
    private UserService userService;
    @GetMapping
    public ApiRespone<?> getAllUsers( ) {
         return ApiRespone.builder()
                .result(userService.getAllUser())
                .build();
    }
    @GetMapping("{iduser}")
    public ApiRespone<?> getUserById(@PathVariable("iduser") UUID idUser) {
        return ApiRespone.builder()
                .result(userService.getUserById(idUser))
                .build();
    }
    @PostMapping
    public ApiRespone<?> postUser(@ModelAttribute @Valid UserRequestDTO requestDTO, @RequestParam(name = "file",required = false) MultipartFile file  ) {
    	  System.out.println("testing here :"+requestDTO.toString());
    	return ApiRespone.builder()
                .result(userService.saveUser(requestDTO,file))
                .build();

    }
    @PutMapping("{id}")
    public ApiRespone<?> putFood(@PathVariable("id") UUID idUser ,@ModelAttribute @Valid UserRequestDTO requestDTO, @RequestParam(name = "file",required = false) MultipartFile file  ) {
    	  System.out.println("testing here :"+requestDTO.toString());
    	return ApiRespone.builder()
                .result(userService.updateUser(idUser,requestDTO,file))
                .build();
    }
    @GetMapping("filter")
    public ApiRespone<?>getFoodFromFilter(@RequestParam(required = false) String  username,
    		@RequestParam(required = false) String fullname,
    		@RequestParam(required = false) String isAdmin,
    		@RequestParam(value = "page", defaultValue = "0") int page,
    	    @RequestParam(value = "size", defaultValue = "10") int size){
        System.out.println(page);
        System.out.println(size);
    	Pageable pageable = PageRequest.of(page, size);
    	  return ApiRespone.builder()
                  .result(userService.getUserFromFilter(username,fullname,isAdmin,pageable))
                  .build();
    }

}
