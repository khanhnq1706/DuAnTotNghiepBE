package com.example.demo.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.request.QrRequestDTO;
import com.example.demo.respone.ApiRespone;
import com.example.demo.respone.QrResposneDTO;
import com.example.demo.service.QrCodeService;
import com.example.demo.service.impl.QrCodeServiceImpl;

@RestController
@RequestMapping("api/QRcode")
public class ManageQRCodeController {

	@Autowired
	QrCodeService qrCodeService;

	@PostMapping
	public ApiRespone<QrResposneDTO> postQR(@RequestBody QrRequestDTO request) {
		ApiRespone<QrResposneDTO> response = new ApiRespone<QrResposneDTO>();
		response.setResult(qrCodeService.createQr(request.getIdTable()));
		return response;
	}

	@PutMapping
	public ApiRespone<QrResposneDTO> reCreateQr(@RequestBody QrRequestDTO request) {
		return qrCodeService.recreateQr(request.getIdTable());
	}

	@GetMapping("{idtable}")
	public ApiRespone<QrResposneDTO> getQrcode(@PathVariable("idtable") int idTable) {
		return qrCodeService.getQrcodeByIdTable(idTable);
	}

	@GetMapping
	public ApiRespone<List<QrResposneDTO>> getAllQrcode() {
		return qrCodeService.getAllQrCode();
	}

}
