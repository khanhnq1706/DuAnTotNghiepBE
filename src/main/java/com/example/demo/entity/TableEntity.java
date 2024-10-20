package com.example.demo.entity;

import com.example.demo.enums.TableStatus;
import com.example.demo.enums.TableStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Where;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
// @Where(clause = "is_Deleted = false")
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_area")
	@JsonBackReference
	AreaEntity area;

}
