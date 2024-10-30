package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.AreaEntity;
import com.example.demo.entity.CategoryFoodEntity;
import com.example.demo.request.AreaRequestDTO;
import com.example.demo.respone.ApiRespone;
import com.example.demo.respone.AreaResponeDTO;

public interface AreaService {
    public List<AreaEntity> findAll();

    public AreaResponeDTO createArea(AreaRequestDTO request);

    public ApiRespone<?> deleteArea(int idArea);

    public AreaResponeDTO updateArea(AreaRequestDTO request, int idArea);

    public ApiRespone<AreaResponeDTO> getArea(int idArea);
}
