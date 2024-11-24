package com.example.demo.entity;

import java.util.Date;

import com.example.demo.enums.PaymentMethod;
import com.example.demo.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Setter
@Getter
public class OrderDetailEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer idOrderDetail;
	@Min(1)
	@Column(nullable = false)
	int quantity;
	@Min(1)
	@Column(nullable = false)
	double price;
	@Min(1)
	@Column(nullable = false)
	double totalPrice;

	@Column(columnDefinition = "nvarchar(500)")
	private String note;
	@NotNull
	@ManyToOne
	@JoinColumn(name = "id_order")
	@JsonBackReference
	OrderEntity orderEntity;
	@NotNull
	@ManyToOne
	@JoinColumn(name = "id_food")
	FoodEntity foodEntity;

}
