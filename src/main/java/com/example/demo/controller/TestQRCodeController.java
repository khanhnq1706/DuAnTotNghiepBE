package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.impl.QrCodeServiceImpl;

@RestController
@RequestMapping("api")
public class TestQRCodeController {

	@Autowired
	private QrCodeServiceImpl qrCodeServiceImpl;
	
//	@PostMapping("testCreateQR")
//	public void postCreateQRTest() {
//		
//		try {
//			qrCodeService.createQrCodeForTable("A1", "28");
//		} catch (WriterException | IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
}
