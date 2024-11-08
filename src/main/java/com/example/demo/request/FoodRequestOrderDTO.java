package com.example.demo.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodRequestOrderDTO {
    @NotNull(message = "ID_FOOD_NOT_NULL")
    Integer idFood;
    Integer quantity;
    String noteFood;
    boolean hasOrder;
}
