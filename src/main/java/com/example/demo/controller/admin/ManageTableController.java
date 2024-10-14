package com.example.demo.controller.admin;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

	    @GetMapping("filter")
	    public ApiRespone<?>getTablesFromFilter(@RequestParam(required = false) String  nameTable,
	    		@RequestParam(required = false) String  status,
	    		@RequestParam(required = false) String location,
	    		@RequestParam(value = "page", defaultValue = "0") int page,
	    		@RequestParam(value = "page", defaultValue = "10") int size){
	    	Pageable pageable = PageRequest.of(page, size);
	    	  return ApiRespone.builder()
	                  .result(tableService.getTablesFromFilter(nameTable, status, location, pageable))
	                  .build();
	   }


}
