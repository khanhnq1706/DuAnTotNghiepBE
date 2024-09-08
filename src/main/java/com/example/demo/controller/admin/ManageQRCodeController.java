package com.example.demo.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.request.QrRequestDTO;
import com.example.demo.respone.ApiRespone;
import com.example.demo.respone.QrResposneDTO;
import com.example.demo.service.impl.QrCodeService;

@RestController
@RequestMapping("api")
public class ManageQRCodeController {

	@Autowired
	QrCodeService qrCodeService ;
	
	@PostMapping("QRcode")
	public ApiRespone<QrResposneDTO> postQR(@RequestBody QrRequestDTO request){
		ApiRespone<QrResposneDTO> response = new ApiRespone<QrResposneDTO>();
		response.setResult(qrCodeService.createQr(request.getIdTable()));
		return response;
	}
	
}
