package com.example.demo.map;

import com.example.demo.entity.OrderEntity;
import com.example.demo.respone.OrderCustomerDTO;
import com.example.demo.respone.OrderResponeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "tableEntity.nameTable", target = "nameTable")
    @Mapping(source = "tableEntity.idTable", target = "idTable")
    @Mapping(source = "tableEntity.area.nameArea", target = "nameArea")
    @Mapping(source = "statusOrder", target = "statusOrder")
    @Mapping(source = "customer.phone", target = "phoneCustomer")
    @Mapping(source = "shift.idShift", target = "idShift")
    @Mapping(source = "tableEntity.idOrderMain", target = "idOrderMain")
    OrderResponeDTO toOrderResponeDTO(OrderEntity order);

    OrderCustomerDTO toOrderCustomerDTO(OrderEntity order);

}
