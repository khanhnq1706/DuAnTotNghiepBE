package com.example.demo.service;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.request.UserRequestDTO;
import com.example.demo.respone.FoodResponeDTO;
import com.example.demo.respone.UserResponeDTO;

public interface UserService {
	 	public Page<UserResponeDTO> getAllUser();
	    public UserResponeDTO saveUser(UserRequestDTO requestDTO, MultipartFile file);
	    public UserResponeDTO updateUser(UUID idUser,UserRequestDTO requestDTO, MultipartFile file);
	    public UserResponeDTO getUserById(UUID idUser);
	    public Page<UserResponeDTO> getUserFromFilter(String username, String fullname, String isAdmin, Pageable pageable);
//		Page<UserResponeDTO> getFoodFromFilter(String username, String fullname, String isAdmin, Pageable pageable);
}
