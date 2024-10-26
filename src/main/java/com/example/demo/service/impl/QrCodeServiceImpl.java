package com.example.demo.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.TableEntity;
import com.example.demo.map.TableMapper;
import com.example.demo.repository.TableRepository;
import com.example.demo.respone.ApiRespone;
import com.example.demo.respone.TableResponseDTO;
import com.example.demo.service.QrCodeService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

@Service
public class QrCodeServiceImpl implements QrCodeService {

	@Autowired
	private TableRepository tableRepository;

	@Autowired
	private TableMapper tableMapper;

	private String hosting = "http://localhost:8080";
	private String hostingFE = "http://192.168.1.17:4200";
	private String formatNameQr = "QRCode_Table_";

	@Override
	public TableResponseDTO createQr(int idTable) {
		TableEntity table = tableRepository.findById(idTable)
				.orElseThrow(() -> new RuntimeException("Table_not_exist"));
		if (table.getLinkImageQr() != null) {
			throw new RuntimeException("QR_exist");
		}
		String nameImg = formatNameQr + table.getNameTable() + ".png";
		Long secretKey = Math.round(Math.random()*10000000);
		table.setNameImageQr(nameImg);
		table.setLinkImageQr(hosting + "/QRCode/" + nameImg);
		table.setSecretKey(secretKey);
		try {
			generateQrCodeForTable(nameImg, idTable,secretKey);
			tableRepository.save(table);
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tableMapper.toTableResponseDTO(table);
	}

	public void generateQrCodeForTable(String nameTable, int idTable,Long key) throws WriterException, IOException {

		String data = hostingFE+ "/?table=" + idTable+"&secretKey="+key;

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

	// Update qrcode mới
	@Transactional
	@Override
	public ApiRespone<TableResponseDTO> recreateQr(int idTable) {
		TableEntity table = tableRepository.findById(idTable)
				.orElseThrow(() -> new RuntimeException("Table_not_exist"));
		if (table.getLinkImageQr() != null) {
			String nameImg = formatNameQr + table.getNameTable() + ".png";
			table.setLinkImageQr(null);
			table.setNameImageQr(null);
			this.deleteQrcode(nameImg);
			System.out.println("Xóa thành công");
			try {
				this.createQr(idTable);
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
		} else {
			return ApiRespone.<TableResponseDTO>builder()
					.message("Không tìm thấy mã QRcode!")
					.build();
		}
		return ApiRespone.<TableResponseDTO>builder()
				.message("Cập nhật QRCode mới thành công!")
				.build();
	}

	// Xóa qrcode cũ
	public void deleteQrcode(String oldimageqrcode) {
		File oldFile = new File("src/main/resources/static/QRCode/" + oldimageqrcode);
		if (oldFile.exists()) {
			oldFile.delete();
		}
	}

	@Override
	public ApiRespone<List<TableResponseDTO>> getAllQrCode() {
		List<TableEntity> tableEntities = tableRepository.findTablesWithQrCode(); // Gọi phương thức mới

		List<TableResponseDTO> tableResponseDTOs = tableEntities.stream()
				.map(tableMapper::toTableResponseDTO)
				.collect(Collectors.toList());

		return ApiRespone.<List<TableResponseDTO>>builder().result(tableResponseDTOs).build();
	}
}
