package com.example.demo.controller.admin;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.enums.TableStatus;
import com.example.demo.request.TableRequestDTO;
import com.example.demo.request.TableStatusRequestDTO;
import com.example.demo.respone.ApiRespone;
import com.example.demo.respone.TableResponseDTO;
import com.example.demo.respone.TableStatusResponeDTO;
import com.example.demo.service.TableService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/tables")
public class ManageTableController {

	@Autowired
	private TableService tableService;

	@GetMapping
	public ApiRespone<?> getAllTables(@RequestParam(required = false, defaultValue = "0") int page,
			@RequestParam(required = false, defaultValue = "17") int size) {
		return ApiRespone.builder().result(tableService.getAllPages(page, size)).build();
	}

	@GetMapping("{id}")
	public ApiRespone<TableResponseDTO> getTable(@PathVariable("id") int idtable) {
		return tableService.getTable(idtable);
	}

	@PostMapping
	public ApiRespone<?> postTable(@Valid @RequestBody TableRequestDTO request) {
		return ApiRespone.builder().result(tableService.saveTables(request)).build();
	}

	@PutMapping("{id}")
	public ApiRespone<?> putTable(@RequestBody TableRequestDTO request,
			@PathVariable("id") int idTable) {
		ApiRespone<TableResponseDTO> response = new ApiRespone<TableResponseDTO>();
		return ApiRespone.builder().result(tableService.updateTable(request, idTable)).build();
	}

	@DeleteMapping("{id}")
	public ApiRespone<?> deleteTable(@PathVariable("id") int idTable) {
		return tableService.deleteTable(idTable);
	}

	// search***********************

	@GetMapping("search")
	public ApiRespone<TableResponseDTO> findTable(
			@RequestParam(value = "tableName", required = false) String tableName) {
		ApiRespone<TableResponseDTO> response = new ApiRespone<TableResponseDTO>();
		response.setResult(tableService.searchTable(tableName));
		return response;
	}

	@GetMapping("by-Status")
	public ApiRespone<?> getTablesByStatus(
			@RequestParam(required = false) TableStatus status,
			@RequestParam(required = false, defaultValue = "0") int page,
			@RequestParam(required = false, defaultValue = "10") int size) {
		return ApiRespone.builder().result(tableService.findTablesByStatus(status, page, size)).build();
	}

	@GetMapping("by-not_deleted")
	public ApiRespone<?> getTableNotDeleted() {
		return ApiRespone.builder().result(tableService.findAllTableNotDelete()).build();
	}

	@GetMapping("getAll-status")
	public ApiRespone<List<TableStatusResponeDTO>> getAllStatus() {
		List<TableStatusResponeDTO> statuses = tableService.getAllStatuses();
		return ApiRespone.<List<TableStatusResponeDTO>>builder().result(statuses).build();
	}

	// update status
	@PutMapping("{id}/status")
	public ApiRespone<?> updateStatus(@PathVariable("id") int id, @RequestBody TableStatusRequestDTO request) {
		return tableService.updateStatus(id, request);
	}
	// **********

	// @GetMapping("by-Capacity")
	// public ApiRespone<?> getTablesCapacity(
	// @RequestParam(required = false) int numberOfGuests,
	// @RequestParam(required = false, defaultValue = "0") int page,
	// @RequestParam(required = false, defaultValue = "10") int size) {
	// return
	// ApiRespone.builder().result(tableService.findAvailableTables(numberOfGuests,
	// page, size)).build();
	// }

}
