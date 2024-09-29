package com.example.demo.controller.admin;

import java.util.List;

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

import com.example.demo.request.TableRequestDTO;
import com.example.demo.respone.ApiRespone;
import com.example.demo.respone.TableResponseDTO;
import com.example.demo.service.TableService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin("*")
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
	public ApiRespone<TableResponseDTO> postTable(@Valid @RequestBody TableRequestDTO request) {
		ApiRespone<TableResponseDTO> response = new ApiRespone<TableResponseDTO>();
		response.setResult(tableService.saveTables(request));
		return response;
	}

	@PutMapping("{id}")
	public ApiRespone<TableResponseDTO> putTable(@RequestBody TableRequestDTO request,
			@PathVariable("id") int idTable) {
		ApiRespone<TableResponseDTO> response = new ApiRespone<TableResponseDTO>();
		try {
			response.setResult(tableService.updateTable(request, idTable));
			response.setMessage("Cập nhật thành công!");
		} catch (RuntimeException e) {
			response.setMessage(e.getMessage());
		}
		return response;
	}

	@DeleteMapping("{id}")
	public ApiRespone<?> deleteTable(@PathVariable("id") int idTable) {
		return tableService.deleteTable(idTable);
	}
}
