package com.example.demo.service.impl;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.demo.entity.BaseEntity;
import com.example.demo.entity.OrderEntity;
import com.example.demo.entity.PromotionEntity;
import com.example.demo.enums.OrderStatus;
import com.example.demo.map.ReportMapper;
import com.example.demo.repository.OrderRepository;
import com.example.demo.request.ReportRequestDTO;
import com.example.demo.respone.ApiRespone;
import com.example.demo.respone.ChartData;
import com.example.demo.respone.ReportData;
import com.example.demo.respone.ReportResponseDTO;
import com.example.demo.service.ReportService;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Service
public class ReportServiceImpl<A> implements ReportService {

	  @Autowired
	    private OrderRepository orderRepository;   
	  
	 
	@Override
	public ReportData getReportData(String startDate, String endDate) {
		 ReportData reportData = new ReportData();
          Specification<OrderEntity> spec = Specification.where(
              (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("statusOrder"),  OrderStatus.Completed)
          );

          if (startDate != null && !startDate.isEmpty() && endDate != null && !endDate.isEmpty()) {
        	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        	  try {
        		  Date startDateParsed = sdf.parse(startDate);
				Date endDateParsed = sdf.parse(endDate);
				endDateParsed.setHours(23);
				endDateParsed.setMinutes(59);
				endDateParsed.setSeconds(59);
				  spec = spec.and(
		                  (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("dateCreate"), startDateParsed, endDateParsed)
		              );
			} catch (ParseException e) {
				e.printStackTrace();
			}

            
          }
          List<OrderEntity> orders = orderRepository.findAll(spec);

          // Tính toán tổng doanh thu và số lượng đơn hàngSS
          double  totalRevenue = orders.stream().mapToDouble(OrderEntity::getTotal).sum();
          int totalOrders = orders.size();
          System.out.println(totalOrders);
          System.out.println(totalRevenue);
          reportData.setTotalRevenue(totalRevenue);
          reportData.setTotalOrders(totalOrders);

          return reportData;
	}
	public List<ChartData> getReportDataGroupBy(String groupBy){
		List<ChartData> doanhSo = new ArrayList<>();
		  Specification<OrderEntity> spec = Specification.where(
	              (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("statusOrder"),  OrderStatus.Completed)
	          );
		  List<OrderEntity> orders = orderRepository.findAll(spec);

		        Map<Object, Double> groupedData = orders.stream()
		                .collect(Collectors.groupingBy(
		                        order -> getLabelByGroup(order, groupBy),
		                        Collectors.summingDouble(OrderEntity::getTotal)
		                ));
		        // Tạo đối tượng ChartData và thêm vào danh sách
		        List<ChartData> chartDataList = groupedData.entrySet().stream()
		                .map(entry -> {
		                    ChartData chartData = new ChartData();
		                    chartData.setLabels(entry.getKey().toString());
		                    chartData.setValues(entry.getValue());
		                    return chartData;
		                })
		                .collect(Collectors.toList());

		        // Sắp xếp danh sách ChartData theo label (ngày/tháng/năm)
		        Collections.sort(chartDataList, Comparator.comparing(ChartData::getLabels));
		  return chartDataList;
	}
	private Object getLabelByGroup(OrderEntity order, String groupBy) {
	    Date date = order.getDateModify(); // Giả sử dateModify là thuộc tính LocalDate
	    SimpleDateFormat formatter;
	    switch (groupBy) {
	    case "day":
            formatter = new SimpleDateFormat("dd/MM/yyyy");
            return formatter.format(date);
        case "month":
            formatter = new SimpleDateFormat("MM/yyyy");
            return formatter.format(date);
        case "year":
            formatter = new SimpleDateFormat("yyyy");
            return formatter.format(date);
	        default:
	            throw new RuntimeException("Invalid groupBy: " + groupBy);
	    }}
}