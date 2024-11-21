package com.example.demo.entity;

import java.util.List;

import com.example.demo.enums.TableStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.*;
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
	Integer idTable;
	String nameTable;
	@Enumerated(EnumType.STRING)
	TableStatus status;
	String linkImageQr;
	String nameImageQr;
	boolean isLocked;
	Long secretKey;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_area")
	@JsonBackReference
	AreaEntity area;
	Integer currentOrderId;
	String currentIP;

	@OneToMany(mappedBy = "tableEntity")
	@JsonIgnore
	List<OrderEntity> orders;

}
