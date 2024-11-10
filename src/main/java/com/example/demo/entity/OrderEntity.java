package com.example.demo.entity;

import java.util.Date;
import java.util.List;

import com.example.demo.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
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
	Double total;
	Boolean isPrinted;
	String namePaymentMethod;
	Date paymentDate;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "id_table")
	@JsonBackReference
	TableEntity tableEntity;

	@ManyToOne
	@JoinColumn(name = "id_User")
	UserEnitty userEnitty;

	@ManyToOne
	@JoinColumn(name = "id_Customer")
	CustomerEntity customer;

	@OneToMany(mappedBy = "orderEntity")
	@JsonManagedReference
	List<OrderDetailEntity> listOrderDetail;

	public double getTotalNeedPayment() {
		return total;
	}

	@ManyToOne
	@JoinColumn(name = "id_Promotion")
	PromotionEntity promotionEntity;

	@ManyToOne
	@JoinColumn(name = "idShift")
	@JsonBackReference
	Shift shift;

}
