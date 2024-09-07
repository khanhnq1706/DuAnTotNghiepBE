package com.example.demo.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

@Service
public class QrCodeService {

	 private String hosting ="http://localhost:8080";
	
	 public  void createQrCode( String nameQrCode, String linkQrCode) throws WriterException, IOException {
	    	
    	 String data =linkQrCode;
     	
         QRCodeWriter qrCodeWriter = new QRCodeWriter();
         BitMatrix matrix =  qrCodeWriter.encode(data, BarcodeFormat.QR_CODE,250,250);

       
         File rootOutputFile = new File("src/main/resources/static/QRCode");
         if(!rootOutputFile.exists()) {
        	 rootOutputFile.mkdirs();
         }
         Path path = Paths.get(rootOutputFile.getAbsolutePath(), nameQrCode+".PNG");
         MatrixToImageWriter.writeToPath(matrix, "PNG", path);

         ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
         MatrixToImageWriter.writeToStream(matrix, "PNG", outputStream);

         System.out.println("Done !");
    }
	 public  void createQrCodeForTable( String nameTable, String idTable) throws WriterException, IOException {
	    	
    	 String data ="http://localhost:8080/userview?table="+idTable;
     	
         QRCodeWriter qrCodeWriter = new QRCodeWriter();
         BitMatrix matrix =  qrCodeWriter.encode(data, BarcodeFormat.QR_CODE,250,250);

       
         File rootOutputFile = new File("src/main/resources/static/QRCode");
         if(!rootOutputFile.exists()) {
        	 rootOutputFile.mkdirs();
         }
         Path path = Paths.get(rootOutputFile.getAbsolutePath(),"QRCode_Table_"+ nameTable+".PNG");
         MatrixToImageWriter.writeToPath(matrix, "PNG", path);

         ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
         MatrixToImageWriter.writeToStream(matrix, "PNG", outputStream);

         System.out.println("Done !");
    }
	
	
}
