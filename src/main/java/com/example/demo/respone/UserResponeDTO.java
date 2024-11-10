package com.example.demo.respone;

import java.util.List;
import java.util.UUID;

import com.example.demo.entity.OrderEntity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
	String password;
	String imgUser;
	Boolean isAdmin;
	Boolean isDeleted;
	Boolean isChangedPass;
}
