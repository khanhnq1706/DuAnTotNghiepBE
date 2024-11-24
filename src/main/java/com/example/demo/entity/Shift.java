package com.example.demo.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity @Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Shift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idShift;
    boolean isWorking;

    @ManyToOne
    @JoinColumn(name = "idStaff")
    @JsonIgnore
    UserEnitty userEnitty;

    @OneToMany(mappedBy = "shift")
    @JsonIgnore
    List<OrderEntity> orders;

    private Double cashAtStart;

    private Double cashAmountEnd;

    private Double bankAmountEnd;

    private Double shiftRevenue;

    private LocalDateTime dateStart;

    private LocalDateTime dateEnd;
}
