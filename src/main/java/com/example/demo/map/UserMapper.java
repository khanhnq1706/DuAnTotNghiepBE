package com.example.demo.map;

import org.mapstruct.Mapper;
import org.springframework.beans.BeanUtils;

import com.example.demo.entity.UserEnitty;
import com.example.demo.request.UserRequestDTO;
import com.example.demo.respone.UserResponeDTO;

@Mapper(componentModel = "spring")
public interface UserMapper {
     default UserEnitty toUserRequestDTO(UserRequestDTO requestDTO) {
    	UserEnitty dto = new UserEnitty();
        BeanUtils.copyProperties(requestDTO, dto);
        return dto;
    }
     default UserResponeDTO toUserResponeDTO(UserEnitty userEnitty) {
    	UserResponeDTO dto = new UserResponeDTO();
        BeanUtils.copyProperties(userEnitty, dto);
        return dto;
    }
}
