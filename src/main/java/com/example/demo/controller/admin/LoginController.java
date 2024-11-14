package com.example.demo.controller.admin;

import com.example.demo.request.AuthenRequest;
import com.example.demo.respone.ApiRespone;
import com.example.demo.respone.AuthenRespone;
import com.example.demo.service.impl.AuthenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api")
public class LoginController {

	@Autowired
	AuthenService authenService;


	@PostMapping("/login")
	public ApiRespone<AuthenRespone> postLogin(@RequestBody AuthenRequest authRequest) {
		ApiRespone<AuthenRespone> respone = authenService.authenAccount(authRequest);
		return respone;
	}

}
