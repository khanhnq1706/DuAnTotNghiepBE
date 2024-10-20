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
import com.example.demo.respone.AreaResponeDTO;
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

    public AreaResponeDTO createArea(AreaRequestDTO request) {
        if (areaRepository.findByNameArea(request.getNameArea()) != null) {
            throw new RuntimeException("Area_exists");
        }
        AreaEntity newArea = areaMapper.toAreaEntity(request);
        return areaMapper.tAreaResponeDTO(areaRepository.save(newArea));
    }

}
