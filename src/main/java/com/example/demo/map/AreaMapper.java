package com.example.demo.map;

import org.mapstruct.Mapper;

import com.example.demo.entity.AreaEntity;
import com.example.demo.request.AreaRequestDTO;
import com.example.demo.respone.AreaResponeDTO;

@Mapper(componentModel = "spring")
public interface AreaMapper {
    AreaEntity toAreaEntity(AreaRequestDTO requestDTO);

    AreaResponeDTO toAreaResponeDTO(AreaEntity areaEntity);
}
