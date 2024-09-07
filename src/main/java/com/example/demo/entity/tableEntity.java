package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor @AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class tableEntity {

	@Id
	int idTable;
	String nameTable;
	
	@OneToOne(mappedBy = "tableEntity")
	QREntity qrEntity;
	
	@OneToOne(mappedBy = "tableEntity")
	OrderEntity orderEntity;
	
}
