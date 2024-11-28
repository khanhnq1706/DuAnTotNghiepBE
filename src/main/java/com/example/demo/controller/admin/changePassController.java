package com.example.demo.controller.admin;

import com.example.demo.request.ChangePassRequestDTO;
import com.example.demo.respone.ApiRespone;
import com.example.demo.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/change-pass")
public class changePassController {

    @Autowired
    UserServiceImpl userService;

    @PostMapping
    public ApiRespone<?> changePass(@RequestBody ChangePassRequestDTO requestDTO){
        return ApiRespone
                .builder()
                .result(userService.changePass(requestDTO))
                .build();
    }

}
