package com.example.demo.entity;

import java.util.List;

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
public class FoodEntity extends BaseEntity {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	int idFood;
	String nameFood;
	float priceFood;
	boolean isSelling;
	String imgFood;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_user")
	UserEnitty userCreated;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_category")
	CategoryFoodEntity listCategory;
	
	@OneToMany(mappedBy = "foodEntity")
	List<OrderDetailEntity> listOrderDetail;
	
}
