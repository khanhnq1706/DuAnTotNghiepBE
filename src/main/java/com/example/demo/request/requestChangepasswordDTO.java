package com.example.demo.request;



import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class requestChangepasswordDTO {
	 @NotBlank(message = "Username_not_blank")
	String username;
	 @NotBlank(message = "Password_not_blank")
	String password;



}
