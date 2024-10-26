package com.example.demo.controller;

import com.example.demo.request.VerifyTableRequestDTO;
import com.example.demo.respone.ApiRespone;
import com.example.demo.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/verify-table")
public class VerifyTableController {
    @Autowired
    TableService tableService;
    @PostMapping
    public ApiRespone<?> verifyTable(@RequestBody VerifyTableRequestDTO Request) {

        return ApiRespone
                .builder()
                .result(tableService.verifyTable(Request))
                .build();
    }
}
