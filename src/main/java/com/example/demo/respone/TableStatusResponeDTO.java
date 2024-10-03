package com.example.demo.respone;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.enums.TableStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableStatusResponeDTO {
    public String status;
    public String displayName;
}
