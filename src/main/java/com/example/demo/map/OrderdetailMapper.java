package com.example.demo.map;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.demo.entity.OrderDetailEntity;
import com.example.demo.respone.OrderDetailResponeDTO;

@Mapper(componentModel = "spring")
public interface OrderdetailMapper {
	@Mapping(source = "foodEntity.idFood", target = "idFood")
    @Mapping(source = "foodEntity.nameFood", target = "namefood")
    @Mapping(source = "foodEntity.discount", target = "discount")
    OrderDetailResponeDTO toOrderDetailResponeDTO(OrderDetailEntity orderdetaientity);
}
