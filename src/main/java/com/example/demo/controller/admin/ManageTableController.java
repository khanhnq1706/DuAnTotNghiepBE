package com.example.demo.controller.admin;

import com.example.demo.enums.TableStatus;
import com.example.demo.request.TableRequestDTO;
import com.example.demo.request.TableStatusCurrentOrderRequestDTO;
import com.example.demo.request.TableStatusRequestDTO;
import com.example.demo.respone.ApiRespone;
import com.example.demo.respone.TableResponseDTO;
import com.example.demo.respone.TableStatusResponeDTO;
import com.example.demo.service.TableService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/tables")
public class ManageTableController {

	@Autowired
	private TableService tableService;

	@GetMapping("sort/asc")
	public ApiRespone<?> getAllTablesASC(
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "17") int size,
			@RequestParam(value = "idArea", defaultValue = "0") int idArea) {
		Page<TableResponseDTO> tablePages = tableService.getAllTablesSortASC(page, size, idArea);
		return ApiRespone.builder()
				.result(tablePages) // Trả về danh sách các bảng
				.build();
	}

	@GetMapping("sort/desc")
	public ApiRespone<?> getAllTablesDESC(
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "17") int size,
			@RequestParam(value = "idArea", defaultValue = "1") int idArea) {

		Page<TableResponseDTO> tablePages = tableService.getAllTablesSortDESC(page, size, idArea);

		return ApiRespone.builder()
				.result(tablePages) // Trả về danh sách các bảng
				.build();
	}

	@GetMapping("filter")
	public ApiRespone<?> getTablesFromFilter(@RequestParam(required = false) String nameTable,
			@RequestParam(required = false) TableStatus status,
			@RequestParam(required = false) Integer idArea,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "17") int size) {
		Pageable pageable = PageRequest.of(page, size);
		return ApiRespone.builder()
				.result(tableService.getTablesFromFilter(nameTable, status, idArea, pageable))
				.build();
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
		return ApiRespone.builder().result(tableService.updateTable(request, idTable)).build();
	}

	@DeleteMapping("{id}")
	public ApiRespone<?> deleteTable(@PathVariable("id") int idTable) {
		return tableService.deleteTable(idTable);
	}

	@GetMapping("by-not_deleted")
	public ApiRespone<?> getTableNotDeleted() {
		return ApiRespone.builder().result(tableService.findAllTableNotlocked()).build();
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

	// Locked table
	@PutMapping("{id}/locked")
	public ApiRespone<?> lockedTable(@PathVariable("id") int idTable) {
		return tableService.lockedTable(idTable);
	}
	@PutMapping("{id}/statusAndCurrent")
	public ApiRespone<?> updateStatusAndCurrent(@PathVariable("id") int id, @RequestBody TableStatusCurrentOrderRequestDTO request) {
		return tableService.updateStatusCurrent(id, request);
	}

}
