package com.example.demo.entity;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
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
	@Column(columnDefinition = "varchar(50)",name = "user_name")
	String username;
	String password;
	String imgUser;
	Boolean isAdmin = false;
	Boolean isDeleted;
	Boolean isChangedPass = false;
	@OneToMany(mappedBy = "userEnitty")
	List<OrderEntity> listOrderEntity;
	
	
}
