package com.jeremias.beprepared.services.impl;

import com.jeremias.beprepared.exceptions.handlers.EntityNotFoundException;
import com.jeremias.beprepared.models.Province;
import com.jeremias.beprepared.repositories.ProvinceRepository;
import com.jeremias.beprepared.services.ProvinceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProvinceServiceImpl implements ProvinceService {
    private final ProvinceRepository provinceRepository;

    @Override
    public List<Province> getAllProvinces() {
        return provinceRepository.findAll();
    }

    @Override
    public Province getProvinceById(Long id) {
        return provinceRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Province not found with such id!")
        );
    }
}
