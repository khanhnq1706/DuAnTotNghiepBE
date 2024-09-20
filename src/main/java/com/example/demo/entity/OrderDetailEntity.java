package com.example.demo.entity;

import com.example.demo.enums.PaymentMethod;
import com.example.demo.enums.PaymentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	int idOrderDetail;
	@Column(nullable = false)
	int quantity;
	@Column(nullable = false)
	long price;
	@Column(nullable = false)
	long totalPrice;
	@Enumerated(EnumType.STRING)
	PaymentStatus paymentStatus;
	@Enumerated(EnumType.STRING)
	PaymentMethod paymentMethod;
	@Column(columnDefinition = "nvarchar(255)")
	String note;
	@ManyToOne
	@JoinColumn(name = "id_order")
	OrderEntity orderEntity;
	
	@ManyToOne
	@JoinColumn(name = "id_food")
	FoodEntity foodEntity;
	
	
	
	int quatity;
}
