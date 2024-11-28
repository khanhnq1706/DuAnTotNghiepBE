package com.example.demo.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShiftRequestDTO {

    UUID idUser;
    Double cashAtStart;
    Double cashAmountEnd;
    Double bankAmountEnd;



}
