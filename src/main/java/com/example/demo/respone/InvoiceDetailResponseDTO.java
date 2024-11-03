package com.example.demo.respone;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@AllArgsConstructor @NoArgsConstructor
@Builder  @Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvoiceDetailResponseDTO  implements Serializable {
    private static final long serialVersionUID = 1L;
    Integer idOrder;
    String nameTable;
    String nameFood;
    float priceFood;
    float discountFood;
    Integer quantityOrder;
    double totalPriceDetailFood;
    double totalOrder;

}
