package com.example.demo.entity;

import java.util.List;

import com.example.demo.enums.OrderStatus;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;


@NoArgsConstructor @AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder @Getter @Setter
public class OrderEntity extends BaseEntity {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	 Integer idOrder;
	@Column(columnDefinition = "varchar(50)")
	@Enumerated(EnumType.STRING)
	private OrderStatus statusOrder;
	double total;
	Boolean isPrinted;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "id_table")
	TableEntity tableEntity;

	@ManyToOne
	@JoinColumn(name = "id_User")
	UserEnitty userEnitty;

	@ManyToOne
	@JoinColumn(name = "id_Customer")
	CustomerEntity customer;

	@OneToMany(mappedBy = "orderEntity")
	List<OrderDetailEntity> listOrderDetail;
	
	@ManyToOne
	@JoinColumn(name = "id_Promotion")
	PromotionEntity promotionEntity;
	

}
