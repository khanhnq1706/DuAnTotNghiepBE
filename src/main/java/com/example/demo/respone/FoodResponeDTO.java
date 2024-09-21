package com.example.demo.respone;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor @NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FoodResponeDTO {
    int idFood;
    String nameFood;
    float priceFood;
    String imgFood;
    Boolean isSelling;
    Boolean isDeleted;
    String note;
    Integer idCategory;
}
