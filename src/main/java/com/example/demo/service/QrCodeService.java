package com.example.demo.service;

import java.util.List;

import com.example.demo.respone.ApiRespone;
import com.example.demo.respone.QrResposneDTO;

public interface QrCodeService {

    QrResposneDTO createQr(int idTable);

    QrResposneDTO recreateQr(int idTable);

    ApiRespone<QrResposneDTO> getQrcodeByIdTable(int idTable);

    ApiRespone<List<QrResposneDTO>> getAllQrCode();
}
