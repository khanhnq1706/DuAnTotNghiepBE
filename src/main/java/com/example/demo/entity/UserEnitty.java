package com.example.demo.entity;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
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
@NoArgsConstructor @AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEnitty {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	UUID idUser;
	String fullname;
	String username;
	String password;
	boolean isAdmin = false;
	boolean isDeleted;

	

	@OneToMany(mappedBy = "userEnitty")
	List<OrderEntity> listOrderEntity;
	
	
}
