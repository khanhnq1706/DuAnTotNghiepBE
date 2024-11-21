package com.example.demo.request;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.enums.TableStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableStatusCurrentOrderRequestDTO {
	Integer currentOrderId;
    @Autowired
    TableStatus status;
    
}
