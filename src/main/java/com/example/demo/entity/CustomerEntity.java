package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Builder @Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerEntity {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    UUID idCustomer;
    String name;
    String phone;
    int rewardPoints;

    @OneToMany(mappedBy = "customer")
    List<OrderEntity> orders;

}
