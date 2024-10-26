package com.example.demo.entity;

import com.example.demo.enums.TableStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
	int idTable;
	String nameTable;
	@Enumerated(EnumType.STRING)
	private TableStatus status;
	String linkImageQr;
	String nameImageQr;
	boolean isLocked;
	Long secretKey;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_area")
	@JsonBackReference
	AreaEntity area;

}
