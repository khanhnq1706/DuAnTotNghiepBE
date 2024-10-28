package com.example.demo.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor @NoArgsConstructor
@Builder @FieldDefaults( level = AccessLevel.PRIVATE)
public class VerifyTableRequestDTO {
    int idTable;
    Long secretKey;
}
