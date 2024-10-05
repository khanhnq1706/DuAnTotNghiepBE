package com.example.demo.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import com.example.demo.respone.ApiRespone;
import com.example.demo.respone.TableResponseDTO;
import com.example.demo.service.TableService;


@RestController
@RequestMapping("api/v1/tables")
public class ManageTableController {

	@Autowired
	private TableService tableService;

	@GetMapping
	public ApiRespone<?> getAllTables(@RequestParam(required = false, defaultValue = "0") int page,
			@RequestParam(required = false, defaultValue = "10") int size) {

		return ApiRespone.builder().result(tableService.getAllPages(page, size)).build();
	}

	@PostMapping
	public ApiRespone<TableResponseDTO> postTable(@RequestBody TableRequestDTO request) {
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
			return response;
		} catch (RuntimeException e) {
			response.setMessage(e.getMessage());
			return response;
		}
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
