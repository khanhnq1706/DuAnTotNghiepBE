package com.example.demo.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Shift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idShift;
    Date date;
    boolean isWorking;

    @ManyToOne
    @JoinColumn(name = "idShiftType")
    @JsonBackReference
    ShiftType shiftType;

    @ManyToOne
    @JoinColumn(name = "idStaff")
    @JsonBackReference
    Staff staff;

    @OneToMany(mappedBy = "shift")
    @JsonManagedReference
    List<OrderEntity> orders;
}
