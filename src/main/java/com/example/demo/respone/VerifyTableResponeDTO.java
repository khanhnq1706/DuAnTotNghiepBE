package com.example.demo.respone;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults( level = AccessLevel.PRIVATE)
public class VerifyTableResponeDTO {
    boolean isAllow;
}
