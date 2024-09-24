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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Where;

@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Where(clause = "is_Deleted = false")
public class FoodEntity extends BaseEntity {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	int idFood;
	String nameFood;
	float priceFood;
 	String imgFood;
	Boolean isSelling;
	Boolean isDeleted;
	String note;

	@ManyToOne
	@JoinColumn(name = "id_category")
	CategoryFoodEntity category;
	
	@OneToMany(mappedBy = "foodEntity")
	List<OrderDetailEntity> listOrderDetail;

	@Override
	public String toString() {
		return "FoodEntity{" +
				"idFood=" + idFood +
				", nameFood='" + nameFood + '\'' +
				", priceFood=" + priceFood +
				", imgFood='" + imgFood + '\'' +
				", isSelling=" + isSelling +
				", isDeleted=" + isDeleted +
				'}';
	}
}
