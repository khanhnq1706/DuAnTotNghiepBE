package com.example.demo.respone;

import com.example.demo.enums.OrderStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor @NoArgsConstructor
@Data @Builder
public class OrderCustomerDTO {
    Integer idOrder;
    @Enumerated(EnumType.STRING)
    private OrderStatus statusOrder;
    double total;
    private Date dateCreate;

}
