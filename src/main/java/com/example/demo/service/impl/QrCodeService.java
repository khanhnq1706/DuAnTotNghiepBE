package com.example.demo.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.QrEntity;
import com.example.demo.entity.TableEntity;
import com.example.demo.map.QrMaper;
import com.example.demo.repository.QrRepository;
import com.example.demo.repository.TableRepository;
import com.example.demo.respone.QrResposneDTO;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

@Service
public class QrCodeService {

	@Autowired
	private QrRepository qrRepository;

	@Autowired
	private TableRepository tableRepository;

	@Autowired
	private QrMaper qrMaper;

	private String hosting = "http://localhost:8080";
	private String formatNameQr = "QRCode_Table_";

	public QrResposneDTO createQr(int idTable) {
		TableEntity table = tableRepository.findById(idTable)
				.orElseThrow(() -> new RuntimeException("Table_not_exist"));
		if (qrRepository.findByTableEntity(table) != null) {
			throw new RuntimeException("QR_exist");
		}
		QrEntity qrCode = new QrEntity();
		String nameImg = formatNameQr + table.getNameTable() + ".png";
		qrCode.setTableEntity(table);
		qrCode.setNameImage(nameImg);
		qrCode.setLinkImage(hosting + "/QRCode/" + nameImg);
		try {
			generateQrCodeForTable(nameImg, idTable);
			qrRepository.save(qrCode);
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return qrMaper.toQRResposneDTO(qrCode);
	}

	public void generateQrCodeForTable(String nameTable, int idTable) throws WriterException, IOException {

		String data = "http://localhost:8080/userview?table=" + idTable;

		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		BitMatrix matrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 250, 250);

		File rootOutputFile = new File("src/main/resources/static/QRCode");
		if (!rootOutputFile.exists()) {
			rootOutputFile.mkdirs();
		}
		Path path = Paths.get(rootOutputFile.getAbsolutePath(), nameTable);
		MatrixToImageWriter.writeToPath(matrix, "PNG", path);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		MatrixToImageWriter.writeToStream(matrix, "PNG", outputStream);

		System.out.println("Done !");
	}

}
