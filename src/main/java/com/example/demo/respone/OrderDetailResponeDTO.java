package com.example.demo.respone;

import com.example.demo.entity.FoodEntity;
import com.example.demo.entity.OrderEntity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailResponeDTO {
    Integer idOrderDetail;
    int quantity;
    int idFood;
    double price;
    double totalPrice;
    String note;
    String nameFood;
    float discount;
    String thumbnail;
}
