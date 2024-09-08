package com.example.demo.map;

import org.mapstruct.Mapper;

import com.example.demo.entity.QrEntity;
import com.example.demo.respone.QrResposneDTO;

@Mapper(componentModel = "spring")
public interface QrMaper {

	QrResposneDTO toQRResposneDTO(QrEntity entity);
	
}
