package com.example.demo.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FoodRequestDTO {

    int idFood;
    @NotBlank(message = "Name_food_not_blank")
    String nameFood;
    @NotNull(message = "Price_food_not_null")
    @Min(value = 0, message = "Price_food_not_negative")
    Float priceFood;
    @NotNull(message = "Is_Selling_not_null")
    String isSelling;

    @NotNull(message = "Id_category_not_null")
    Integer idCategory;
    String note;
}
