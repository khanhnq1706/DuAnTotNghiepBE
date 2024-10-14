/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.example.demo.service;

import com.example.demo.repository.FoodRepository;
import com.example.demo.request.FoodRequestDTO;
import com.example.demo.respone.FoodResponeDTO;
import com.example.demo.service.impl.FoodServiceImpl;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Dell
 */
public class FoodServiceTest {
     @Autowired
    private FoodService foodService;

    @MockBean
    private FoodRepository foodRepository;


//    public FoodServiceTest() {
//    }
//    
//    @BeforeAll
//    public static void setUpClass() {
//    }
//    
//    @AfterAll
//    public static void tearDownClass() {
//    }
//    
//    @BeforeEach
//    public void setUp() {
//    }
//    
//    @AfterEach
//    public void tearDown() {
//    }

    /**
     * Test of getAllFood method, of class FoodService.
     */
//    @Test
//    public void testGetAllFood() {
//        System.out.println("getAllFood");
//        int page = 0;
//        int size = 0;
//        FoodService instance = new FoodServiceImpl();
//        Page<FoodResponeDTO> expResult = null;
//        Page<FoodResponeDTO> result = instance.getAllFood(page, size);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of saveFood method, of class FoodService.
     */
//    @Test
//    public void testSaveFood() {
//        System.out.println("saveFood");
//        FoodRequestDTO requestDTO = new FoodRequestDTO(1, "Pasta", 240000.0f, "true", "false", 1, "Mon an moi");
//         MultipartFile file = mock(MultipartFile.class);
//    when(file.getOriginalFilename()).thenReturn("pasta.jpg");
////        FoodService instance = new FoodServiceImpl(foodRepository);
//
////        FoodResponeDTO result = instance.saveFood(requestDTO, file);
//       assertNotNull(result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("This food doesn't exits");
//    }

    /**
     * Test of updateFood method, of class FoodService.
     */
//    @Test
//    public void testUpdateFood() {
//        System.out.println("updateFood");
//       int idFood = 1;
//        FoodRequestDTO requestDTO = new FoodRequestDTO(1, "Pasta", 240000.0f, "true", "false", 1, "Mon an moi");
//         MultipartFile file = mock(MultipartFile.class);
//    when(file.getOriginalFilename()).thenReturn("pasta.jpg");
//        FoodService instance = new FoodServiceImpl();
//        FoodResponeDTO expResult = null;
//        FoodResponeDTO result = instance.updateFood(idFood, requestDTO, file);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("This food is exits.");
//    }

 
    
}
