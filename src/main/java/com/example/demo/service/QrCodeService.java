package com.example.demo.service;

import java.util.List;

import com.example.demo.respone.ApiRespone;
import com.example.demo.respone.TableResponseDTO;

public interface QrCodeService {

    TableResponseDTO createQr(int idTable);

    ApiRespone<TableResponseDTO> recreateQr(int idTable);

    ApiRespone<List<TableResponseDTO>> getAllQrCode();
}
