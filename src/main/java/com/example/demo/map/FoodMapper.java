package com.example.demo.map;

import com.example.demo.entity.FoodEntity;
import com.example.demo.request.FoodRequestDTO;
import com.example.demo.respone.FoodResponeDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FoodMapper {

    FoodEntity toFoodEntity (FoodRequestDTO requestDTO);
    FoodResponeDTO toFoodResponeDTO(FoodEntity foodEntity);

}
