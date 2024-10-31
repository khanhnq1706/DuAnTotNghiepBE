package com.example.demo.respone;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data @NoArgsConstructor @AllArgsConstructor
public class OrderResponeDTO {

    Integer idOrder;
    String statusOrder;
    String nameTable;
    String phoneCustomer;
    Float total;
    Boolean isPrinted;
}
