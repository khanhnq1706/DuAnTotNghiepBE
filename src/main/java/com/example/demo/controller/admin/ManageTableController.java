package com.example.demo.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.request.TableRequestDTO;
import com.example.demo.respone.ApiRespone;
import com.example.demo.respone.TableResponseDTO;
import com.example.demo.service.impl.TableServiceImpl;

@RestController
@RequestMapping("api")
public class ManageTableController {

	@Autowired
	private TableServiceImpl tableServiceImpl;

	@GetMapping("tables")

	public ApiRespone<List<TableResponseDTO>> getTable(@RequestBody TableRequestDTO request) {
		ApiRespone<List<TableResponseDTO>> response = new ApiRespone<List<TableResponseDTO>>();
		response.setResult(tableServiceImpl.getAllTable());
		return response;
	}



	@PostMapping("table")
	public ApiRespone<TableResponseDTO> postTable(@RequestBody TableRequestDTO request) {
		ApiRespone<TableResponseDTO> response = new ApiRespone<TableResponseDTO>();
		response.setResult(tableServiceImpl.saveTable(request));
		return response;
	}

	@PutMapping("table/{id}")
	public ApiRespone<TableResponseDTO> putTable(@RequestBody TableRequestDTO request,@PathVariable("id") int idTable) {
		ApiRespone<TableResponseDTO> response = new ApiRespone<TableResponseDTO>();
		response.setResult(tableServiceImpl.updateTable(request, idTable));
		return response;
	}
	
	@DeleteMapping("table/{id}")
	public ApiRespone<TableResponseDTO> deleteTable(@RequestBody TableRequestDTO request,@PathVariable("id") int idTable) {
		ApiRespone<TableResponseDTO> response = new ApiRespone<TableResponseDTO>();
//		response.setResult(tableService.updateTable(request, idTable));
		return response;
	}
	
}
