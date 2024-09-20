package com.example.demo.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FoodRequestDTO {
    int idFood;
    String nameFood;
    float priceFood;
//    String imgFood;
    boolean isSelling;
    boolean isDeleted;
    int idCategory;
}
