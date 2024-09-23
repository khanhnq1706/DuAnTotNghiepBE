package com.example.demo.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor @AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryFoodEntity extends BaseEntity {
	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	int idCategory;
	boolean isDeleted;

	@Column(columnDefinition = "varchar(100)")
	String nameCategory;

	@OneToMany(mappedBy = "category")
	@JsonManagedReference
	List<FoodEntity> listFoodCreated;
}
