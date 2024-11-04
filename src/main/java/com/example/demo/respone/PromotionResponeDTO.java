package com.example.demo.respone;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromotionResponeDTO {
    int idPromotion;
    String namePromotion;
    float discount;
    Date startDate;
    Date endDate;
    String description;
}
