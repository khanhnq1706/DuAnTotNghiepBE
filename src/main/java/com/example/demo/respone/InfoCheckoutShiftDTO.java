package com.example.demo.respone;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor @Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InfoCheckoutShiftDTO {
    Double cashAtStart;
    Double cashAmountEnd;
    Double bankAmountEnd;
    Double shiftRevenue;
    Double totalServing;
}
