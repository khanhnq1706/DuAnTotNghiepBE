package com.example.demo.controller.admin;

import com.example.demo.request.AuthenRequest;
import com.example.demo.request.LogoutRequest;
import com.example.demo.respone.ApiRespone;
import com.example.demo.respone.AuthenRespone;
import com.example.demo.service.impl.AuthenService;
import com.nimbusds.jose.JOSEException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;


@RestController
@RequestMapping("api")
public class LoginLogoutController {

	@Autowired
	AuthenService authenService;


	@PostMapping("/login")
	public ApiRespone<AuthenRespone> postLogin(@RequestBody AuthenRequest authRequest) {
		ApiRespone<AuthenRespone> respone = authenService.authenAccount(authRequest);
		return respone;
	}
	@PostMapping("/logout")
	public ApiRespone<?> postLogout(@RequestBody LogoutRequest authRequest) throws ParseException, JOSEException {
		authenService.logout(authRequest.getToken());
		return ApiRespone.builder().build();
	}

	@PostMapping("/testVerify")
	public ApiRespone<?> postVerify(@RequestBody LogoutRequest authRequest) throws ParseException, JOSEException {
		authenService.verifyToken(authRequest.getToken());
		return ApiRespone.builder().build();
	}

}
