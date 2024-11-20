package com.example.demo.request;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class PromotionRequestDTO {
    @NotBlank(message = "Name_promotion_not_blank")
    String namePromotion;
    @NotNull(message = "Discount_not_null")
    @Min(value = 0, message = "Discount_not_negative")
    float discount;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date endDate;
    String isIncreasePrice;
    String description;
    @NotNull
    boolean isDeleted;

    
}
