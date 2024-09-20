package com.example.demo.entity;

import java.util.Date;
import java.util.List;

import com.example.demo.enums.OrderStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor @AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderEntity extends BaseEntity {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int idOrder;
	 @Enumerated(EnumType.STRING)
	OrderStatus statusOrder;
	 @Temporal(TemporalType.DATE)
	Date createdDate;
	 @Temporal(TemporalType.DATE)
	Date updatedDate;
	@OneToOne
	@JoinColumn(name = "id_table")
	TableEntity tableEntity;
	@ManyToOne
	@JoinColumn(name = "id_User")
	UserEnitty userEnitty;
	
	@OneToMany(mappedBy = "orderEntity")
	List<OrderDetailEntity> listOrderDetail;
	public void preCreate() {
		createdDate = new Date();
	}
}
