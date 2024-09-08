package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor @AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "QR_Entity")
public class QrEntity {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	int idQr;
	String nameImage;
	String linkImage;
	@OneToOne
	@JoinColumn(name = "id_table")
	TableEntity tableEntity;
	
	
}
