package com.example.demo.service.impl;

import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.Specification.UserSpecs;
import com.example.demo.entity.UserEnitty;
import com.example.demo.map.UserMapper;
import com.example.demo.repository.UserRepository;
import com.example.demo.request.UserRequestDTO;
import com.example.demo.respone.UserResponeDTO;
import com.example.demo.service.UserService;



@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private FileService fileService;
	@Override
	public Page<UserResponeDTO> getAllUser() {
		return new PageImpl<>(userRepository.findAll().stream().map(userMapper::toUserResponeDTO).collect(Collectors.toList()));
	}
	@Transactional
	@Override
	public UserResponeDTO saveUser(UserRequestDTO requestDTO, MultipartFile file) {
		UserEnitty userEnitty = userRepository.findByUsername(requestDTO.getUsername().trim());	
		if (userEnitty != null) {
			throw new RuntimeException("FOOD_ALREADY_EXISTS");
		}
		userEnitty = userMapper.toUserRequestDTO(requestDTO);
		if (file != null) {
			System.out.println(file.getOriginalFilename());

			userEnitty.setImgUser(file.getOriginalFilename());
			fileService.saveFile(file);
		}
		return userMapper.toUserResponeDTO(userRepository.save(userEnitty));
	}
	@Transactional
	@Override
	public UserResponeDTO updateUser(UUID idUser, UserRequestDTO requestDTO, MultipartFile file) {
		
		UserEnitty userEnitty = userRepository.findById(idUser)
				.orElseThrow(() -> new RuntimeException("FOOD_NOT_EXISTS"));
		String imgUserTemp = userEnitty.getImgUser();
		userEnitty = userRepository.findByUsername(requestDTO.getUsername().trim());
		if (userEnitty != null && userEnitty.getIdUser()!=idUser ) {
			throw new RuntimeException("FOOD_ALREADY_EXISTS");
		}
		userEnitty = userMapper.toUserRequestDTO(requestDTO);
		if (file != null && !file.getOriginalFilename().trim().equals("")) {
			imgUserTemp = file.getOriginalFilename();
			fileService.saveFile(file);
		}
		
		userEnitty.setIdUser(idUser);
		userEnitty.setImgUser(imgUserTemp);
		userRepository.save(userEnitty);

		return userMapper.toUserResponeDTO(userEnitty);	
	}
	@Override
	public UserResponeDTO getUserById(UUID idUser) {
		UserEnitty userEnitty = userRepository.findById(idUser)
				.orElseThrow(() -> new RuntimeException(" FOOD_NOT_EXISTS"));
		UserResponeDTO responeDTO = userMapper.toUserResponeDTO(userEnitty);
		return responeDTO;
	}
	@Override
	public Page<UserResponeDTO> getUserFromFilter(String username, String fullname, String isAdmin, Pageable pageable) {

		Specification<UserEnitty> specsUser = Specification.where(
				UserSpecs.hasUserName(username)
				.and(UserSpecs.hasFullName(fullname))
				.and(UserSpecs.isAdmin(isAdmin)));
		return	userRepository.findAll(specsUser, pageable).map(userMapper::toUserResponeDTO);

	}

}
