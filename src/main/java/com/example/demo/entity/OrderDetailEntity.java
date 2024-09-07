package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor @AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailEntity  {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	int idOrderDetail;
	
	@ManyToOne
	@JoinColumn(name = "id_order")
	OrderEntity orderEntity;
	
	@ManyToOne
	@JoinColumn(name = "id_food")
	FoodEntity foodEntity;
	
	
	
	int quatity;
}
