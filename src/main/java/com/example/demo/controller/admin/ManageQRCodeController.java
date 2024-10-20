package com.example.demo.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.request.QrRequestDTO;
import com.example.demo.respone.ApiRespone;
import com.example.demo.respone.TableResponseDTO;
import com.example.demo.service.QrCodeService;
import com.example.demo.service.impl.QrCodeServiceImpl;

@RestController
@RequestMapping("api/QRcode")
public class ManageQRCodeController {

	@Autowired
	QrCodeService qrCodeService;

	@GetMapping
	public ApiRespone<List<TableResponseDTO>> getAllQr() {
		return qrCodeService.getAllQrCode();
	}

	@PostMapping
	public ApiRespone<TableResponseDTO> postQR(@RequestBody TableResponseDTO request) {
		ApiRespone<TableResponseDTO> response = new ApiRespone<TableResponseDTO>();
		response.setResult(qrCodeService.createQr(request.getIdTable()));
		return response;
	}

	@PutMapping
	public ApiRespone<TableResponseDTO> reCreateQr(@RequestBody TableResponseDTO request) {
		return qrCodeService.recreateQr(request.getIdTable());
	}

}
