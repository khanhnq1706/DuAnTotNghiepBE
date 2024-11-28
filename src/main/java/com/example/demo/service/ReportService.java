package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.example.demo.request.ReportRequestDTO;
import com.example.demo.respone.ApiRespone;
import com.example.demo.respone.ChartData;
import com.example.demo.respone.ReportData;
import com.example.demo.respone.ReportResponse;

public interface ReportService {


	ReportResponse getReportData(String startDate, String endDate, String groupBy);	

}
