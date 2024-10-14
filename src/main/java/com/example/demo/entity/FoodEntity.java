package com.example.demo.entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Where;


@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Where(clause = "is_Deleted = false")
public class FoodEntity extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int idFood;
	String nameFood;
	float priceFood;

 	String imgFood;
	Boolean isSelling;
	Boolean isDeleted;
	@Column(columnDefinition = "nvarchar(1000)")
	String note;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_category")
	@Fetch(FetchMode.JOIN)
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
