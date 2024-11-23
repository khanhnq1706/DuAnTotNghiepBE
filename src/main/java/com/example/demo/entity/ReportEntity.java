package com.example.demo.entity;

import java.util.Date;

import com.example.demo.enums.ReportFormat;
import com.example.demo.enums.ReportType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ReportEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private ReportType type; // DOANH_THU, LOI_NHUAN, DON_HANG

    @Column(columnDefinition = "TEXT")
    private String criteria; // JSON chứa các tiêu chí lọc (ví dụ: sản phẩm, khách hàng, khoảng thời gian)

    @Column(columnDefinition = "TEXT")
    private String fields; // JSON chứa các trường cần hiển thị

    @Enumerated(EnumType.STRING)
    private ReportFormat format; // PDF, EXCEL, CSV

    @Column(columnDefinition = "TIMESTAMP")
    private Date schedule;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private UserEnitty user;
}
