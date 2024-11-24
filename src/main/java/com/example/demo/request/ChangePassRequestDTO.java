package com.example.demo.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChangePassRequestDTO {
    UUID idUser;
    @NotBlank(message = "PASSWORD_NOt_NULL")
    String password;

}
