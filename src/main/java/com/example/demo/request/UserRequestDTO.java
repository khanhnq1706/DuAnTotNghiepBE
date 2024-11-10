package com.example.demo.request;

import java.util.UUID;

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
public class UserRequestDTO {
	UUID idUser;
	 @NotBlank(message = "Fullname_not_blank")
	String fullname;
	 @NotBlank(message = "Username_not_blank")
	String username;
	 @NotBlank(message = "Password_not_blank")
	String password;
	String imgUser;
	Boolean isAdmin;
	Boolean isDeleted;
	Boolean isChangedPass;
}
