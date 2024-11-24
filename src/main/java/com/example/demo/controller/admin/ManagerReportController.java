package com.example.demo.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.request.ReportRequestDTO;
import com.example.demo.respone.ApiRespone;
import com.example.demo.respone.ReportData;
import com.example.demo.service.ReportService;

@RestController
@RequestMapping("/api/v1/reports")
public class ManagerReportController {
	@Autowired
    private ReportService reportService;
	
	@GetMapping("filter")
	public ApiRespone<?> getReport(@RequestParam(required = false) String startDate,
	                               @RequestParam(required = false) String endDate) {

	        return ApiRespone.builder()
	                .result(reportService.getReportData(startDate, endDate))
	                .build();
	
}
	@GetMapping("filterchart")
	public ApiRespone<?> getChartData(@RequestParam(required = false) String groupBy) {

	        return ApiRespone.builder()
	                .result(reportService.getReportDataGroupBy(groupBy))
	                .build();
	
}
	}
