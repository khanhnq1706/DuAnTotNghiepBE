package com.example.demo.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.AreaEntity;
import com.example.demo.entity.CategoryFoodEntity;
import com.example.demo.entity.TableEntity;
import com.example.demo.map.AreaMapper;
import com.example.demo.repository.AreaRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.request.AreaRequestDTO;
import com.example.demo.request.TableRequestDTO;
import com.example.demo.respone.ApiRespone;
import com.example.demo.respone.AreaResponeDTO;
import com.example.demo.respone.TableResponseDTO;
import com.example.demo.service.AreaService;

@Service
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaRepository areaRepository;
    @Autowired
    private AreaMapper areaMapper;

    @Override
    public List<AreaEntity> findAll() {
        return areaRepository.findAll();
    }

    @Override
    public ApiRespone<AreaResponeDTO> getArea(int idArea) {
        AreaEntity area = areaRepository.findById(idArea)
                .orElseThrow(() -> new RuntimeException("Table_not_found"));
        AreaResponeDTO responseDTO = areaMapper.toAreaResponeDTO(area);
        return ApiRespone.<AreaResponeDTO>builder()
                .result(responseDTO)
                .build();
    }

    @Override
    public AreaResponeDTO createArea(AreaRequestDTO request) {
        if (areaRepository.findByNameArea(request.getNameArea()) != null) {
            throw new RuntimeException("Area_exists");
        }
        AreaEntity newArea = areaMapper.toAreaEntity(request);
        return areaMapper.toAreaResponeDTO(areaRepository.save(newArea));
    }

    @Override
    public ApiRespone<?> deleteArea(int idArea) {
        AreaEntity area = areaRepository.findById(idArea)
                .orElseThrow(() -> new RuntimeException("Table_not_found"));
        areaRepository.deleteById(idArea);
        return ApiRespone.builder().message("Table delete successfully!").build();
    }

    // Update
    @Override
    public AreaResponeDTO updateArea(AreaRequestDTO request, int idArea) {
        AreaEntity area = areaRepository.findById(idArea)
                .orElseThrow(() -> new RuntimeException("Table_not_found"));
        if (!area.getNameArea().equals(request.getNameArea()) &&
                areaRepository.findByNameArea(request.getNameArea()) != null) {
            throw new RuntimeException("Table_exists");
        }
        area.setNameArea(request.getNameArea().trim());
        return areaMapper.toAreaResponeDTO(areaRepository.save(area));
    }

}
