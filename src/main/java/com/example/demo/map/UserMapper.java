package com.example.demo.map;

import org.mapstruct.Mapper;
import com.example.demo.entity.UserEnitty;
import com.example.demo.request.UserRequestDTO;
import com.example.demo.respone.UserResponeDTO;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEnitty toUserEntity(UserRequestDTO requestDTO);
    UserResponeDTO toUserResponeDTO(UserEnitty userEnitty);
}
