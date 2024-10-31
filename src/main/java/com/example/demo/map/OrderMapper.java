package com.example.demo.map;

import com.example.demo.entity.OrderEntity;
import com.example.demo.respone.OrderResponeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "tableEntity.idTable",target = "nameTable")
    @Mapping(source = "statusOrder",target = "statusOrder")
    @Mapping(source = "customer.phone",target = "phoneCustomer")
    OrderResponeDTO toOrderResponeDTO(OrderEntity order);



}
