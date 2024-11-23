package com.example.demo.entity;

import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class ShiftType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idShiftType;
    String nameShift;
    LocalTime startTime;
    LocalTime endTime;
    String description;

    @OneToMany(mappedBy = "shiftType")
    @JsonManagedReference
    List<Shift> shifts;
}
