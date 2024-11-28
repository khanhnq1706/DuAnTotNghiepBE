package com.example.demo.request;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.demo.enums.ReportType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportRequestDTO {
	@Enumerated(EnumType.STRING)
//    private ReportType type; // DOANH_THU, LOI_NHUAN, DON_HANG
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date endDate;
}
