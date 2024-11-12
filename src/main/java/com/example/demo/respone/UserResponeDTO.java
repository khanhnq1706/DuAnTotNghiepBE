package com.example.demo.respone;

import java.util.UUID;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor @NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponeDTO {
	UUID idUser;
	String fullname;
	String username;
	String imgUser;
	Boolean isAdmin;
	Boolean isDeleted;
	Boolean isChangedPass;
}
