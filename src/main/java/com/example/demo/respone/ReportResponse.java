package com.example.demo.respone;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReportResponse {
	 private ReportData reportData;
	 private List<ChartData> chartData;

}
