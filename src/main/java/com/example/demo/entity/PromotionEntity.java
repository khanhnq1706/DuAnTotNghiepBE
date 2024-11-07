package com.example.demo.entity;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PromotionEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    int idPromotion;
	@Column(columnDefinition = "nvarchar(255)")
    String namePromotion;
    float discount;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    Date startDate;	
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    Date endDate;
    @Column(columnDefinition = "nvarchar(3000)")
    String description;
    boolean isDeleted;
	@OneToMany(mappedBy = "promotionEntity")
	List<OrderEntity> listOrder;
	@Override
	public String toString() {
		return "PromotionEntity{" +
				"idPromotion=" + idPromotion +
				", namePromotion='" + namePromotion + '\'' +
				", discount=" + discount +
				", startDate='" + startDate + '\'' +
				", endDate=" + endDate +
				'}';
	}
}
