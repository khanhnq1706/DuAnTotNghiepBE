package com.example.demo.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileService {

	public void saveFile(MultipartFile file) {
		String uploadDir = "src/main/resources/static/images/";
		File fileRootImg = new File(uploadDir);

		if (!fileRootImg.exists()) {
			fileRootImg.mkdirs();
		}

		if (file != null) {
			File fileImg = new File(fileRootImg.getAbsolutePath(), file.getOriginalFilename());
			try {
				file.transferTo(fileImg);
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
		}
	}
	
}
