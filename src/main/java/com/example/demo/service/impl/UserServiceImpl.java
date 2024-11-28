package com.example.demo.service.impl;

import java.util.UUID;
import java.util.stream.Collectors;

import com.example.demo.request.ChangePassRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import com.example.demo.util.Encryption;



@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private PasswordEncoder passwordEncoder ;

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
			throw new RuntimeException("USER_EXISTED");
		}
		
		userEnitty = userMapper.toUserEntity(requestDTO);
		
		if (file != null) {
			System.out.println(file.getOriginalFilename());

			userEnitty.setImgUser(file.getOriginalFilename());
			fileService.saveFile(file);
		}
		userEnitty.setPassword("123");
		userEnitty.setIsChangedPass(false);
		userEnitty.setPassword(Encryption.toSHA1(userEnitty.getPassword()));
		return userMapper.toUserResponeDTO(userRepository.save(userEnitty));
	}
	@Transactional
	@Override
	public UserResponeDTO updateUser(UUID idUser, UserRequestDTO requestDTO, MultipartFile file) {    
	    UserEnitty userEntity = userRepository.findById(idUser)
	            .orElseThrow(() -> new RuntimeException("USER_NOT_EXISTS"));
	   
	    String newPassword = requestDTO.getPassword();
	    System.out.println(newPassword);
	    if (newPassword==null || newPassword.trim().isEmpty()) {
	    	System.out.println(userEntity.getPassword());
	        requestDTO.setPassword(userEntity.getPassword());
	    } else {
	        requestDTO.setPassword(Encryption.toSHA1(newPassword));
	    }
	    
	    UserEnitty existingUser = userRepository.findByUsername(requestDTO.getUsername().trim());
	    if (existingUser != null && !existingUser.getIdUser().equals(idUser)) {
	        throw new RuntimeException("USERNAME_ALREADY_EXISTS");
	    }
	    userEntity.setFullname(requestDTO.getFullname());
	    userEntity.setUsername(requestDTO.getUsername());
	    userEntity.setIsAdmin(requestDTO.getIsAdmin());
	    userEntity.setIsDeleted(requestDTO.getIsDeleted());
	    userEntity.setPassword(requestDTO.getPassword());	
	    if(BCrypt.checkpw("123",userEntity.getPassword())) {
	    	
	    	userEntity.setIsChangedPass(false);
		}else {
			userEntity.setIsChangedPass(true);
		}
	    if (file != null && !file.getOriginalFilename().trim().isEmpty()) {
	        String imgUserTemp = file.getOriginalFilename();
	        fileService.saveFile(file); 
	        userEntity.setImgUser(imgUserTemp); 
	    }

	  
	    userRepository.save(userEntity);

	    return userMapper.toUserResponeDTO(userEntity);
	}

	@Override
	public UserResponeDTO getUserById(UUID idUser) {
		UserEnitty userEnitty = userRepository.findById(idUser)
				.orElseThrow(() -> new RuntimeException("USER_NOT_EXISTS"));
		UserResponeDTO responeDTO = userMapper.toUserResponeDTO(userEnitty);
		return responeDTO;
	}
	@Override
	public Page<UserResponeDTO> getUserFromFilter(String username, String fullname, String isAdmin, String IsChangedPass, Pageable pageable) {
		Specification<UserEnitty> specsUser = Specification.where(
				UserSpecs.hasUserName(username)
				.and(UserSpecs.hasFullName(fullname))
				.and(UserSpecs.isAdmin(isAdmin))
				.and(UserSpecs.isChangedPass(IsChangedPass)));
		return	userRepository.findAll(specsUser, pageable).map(userMapper::toUserResponeDTO);

	}
//	@Override
//	public Page<UserResponeDTO> getUserFromFilter(String username, String fullname, String isAdmin, Pageable pageable) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	public UserResponeDTO changePass(ChangePassRequestDTO requestDTO) {
		UserEnitty userEnitty = userRepository.findById(requestDTO.getIdUser())
				.orElseThrow(() -> new RuntimeException("USER_NOT_EXISTS"));
		userEnitty.setPassword(passwordEncoder.encode(requestDTO.getPassword()));

		if(userEnitty.getIsChangedPass()){
			throw new RuntimeException("PASSWORD_CHANGED");
		}
		userEnitty.setIsChangedPass(true);
		return  userMapper.toUserResponeDTO(userRepository.save(userEnitty));
	}

}
