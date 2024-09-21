package com.example.demo.entity;

import java.util.Date;

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
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
	private int idOrderDetail;
	@Min(1)
    @Max(15)
	@Column(nullable = false)
	private int quantity;
	@Min(1)
	@Column(nullable = false)
	private long price;
	@Column(nullable = false)
	private long totalPrice;
	@NotNull
	@Enumerated(EnumType.STRING)
	@NotNull
	PaymentStatus paymentStatus;
	@NotNull
	@Enumerated(EnumType.STRING)
	PaymentMethod paymentMethod;
	@Column(columnDefinition = "nvarchar(255)")
	private String note;
	@ManyToOne
	@JoinColumn(name = "id_order")
	OrderEntity orderEntity;
	
	@ManyToOne
	@JoinColumn(name = "id_food")
	FoodEntity foodEntity;
	@PrePersist
	@PreUpdate
	public void preTotal() {
	    if (quantity < 0 || price < 0) {
	        throw new IllegalArgumentException("Số lượng hoặc đơn giá không hợp lệ");
	    }
	    totalPrice = quantity * price;
//	    if (discount > 0) {
//	        totalPrice -= totalPrice * discount / 100;
//	    }
	}
}
