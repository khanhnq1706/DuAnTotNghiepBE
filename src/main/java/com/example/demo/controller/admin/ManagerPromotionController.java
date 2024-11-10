package com.example.demo.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.request.PromotionRequestDTO;
import com.example.demo.respone.ApiRespone;

import com.example.demo.service.PromotionService;

import jakarta.validation.Valid;
@RestController
@RequestMapping("api/v1/promotions")
public class ManagerPromotionController {
	  @Autowired
	    private PromotionService promotionService;

	    @GetMapping
	    public ApiRespone<?> getAllPromotions( ) {
	         return ApiRespone.builder()
	                .result(promotionService.getAllPromotion())
	                .build();
	    }
	    @GetMapping("{idPromotion}")
	    public ApiRespone<?> getPromotionById(@PathVariable("idPromotion") int idPromotion) {
	        return ApiRespone.builder()
	                .result(promotionService.getFoodById(idPromotion))
	                .build();
	    }
	    @PostMapping
	    public ApiRespone<?> postPromotion(@ModelAttribute PromotionRequestDTO requestDTO ) {
	        System.out.println(requestDTO.toString());
	        return ApiRespone.builder()
	                .result(promotionService.savePromotion(requestDTO))
	                .build();

	    }
	    
	    @PutMapping("{idPromotion}")
	    public ApiRespone<?> putPromotion(@PathVariable("idPromotion") int idPromotion ,@ModelAttribute @Valid PromotionRequestDTO requestDTO ) {
	     
	        return ApiRespone.builder()
	                .result(promotionService.updatePromotion(idPromotion,requestDTO))
	                .build();
	    }
	    @DeleteMapping("{idPromotion}")
		public ApiRespone<?> deleteTable(@PathVariable("idPromotion") int idPromotion) {
	    	 return ApiRespone.builder()
		                .result(promotionService.deletePromotion(idPromotion))
		                .build();
		                
			
		}
	    @GetMapping("filter")
	    public ApiRespone<?> getPromotionFromFilter(
	            @RequestParam(required = false) String namePromotion,
	            @RequestParam(required = false) String status,
	    		@RequestParam(value = "sortBy",required = false) String sortField,
	    		@RequestParam(value = "orderBy", required = false) String sortDirection,
	            @RequestParam(value = "page", defaultValue = "0") int page,
	            @RequestParam(value = "size", defaultValue = "10") int size
	           ) {
	        Pageable pageable = PageRequest.of(page, size);
	        return ApiRespone.builder()
	                .result(promotionService.getPromotionFromFilter(namePromotion, status,sortField,sortDirection, pageable))
	                .build();
	    }

	    
//	    @GetMapping("filter")
//	    public ApiRespone<?>getPromotionFromFilter(@RequestParam(required = false) String  namePromotion,
//	    		@RequestParam(required = false) String status,
//	    		@RequestParam(value = "page", defaultValue = "0") int page,
//	    	    @RequestParam(value = "size", defaultValue = "10") int size){
//	        System.out.println(page);
//	        System.out.println(size);
//	    	Pageable pageable = PageRequest.of(page, size);
//	    	  return ApiRespone.builder()
//	                  .result(promotionService.getPromotionFromFilter(namePromotion,status,pageable))
//	                  .build();
//	    }
}
