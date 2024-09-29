package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class TableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int idTable;
	String nameTable;
	boolean isDeleted;

	@JsonIgnore
	@OneToOne(mappedBy = "tableEntity")
	QrEntity qrEntity;

	@JsonIgnore
	@OneToOne(mappedBy = "tableEntity")
	OrderEntity orderEntity;

}
