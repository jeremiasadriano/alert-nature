package com.jeremias.beprepared.services.impl;

import com.jeremias.beprepared.exceptions.handlers.EntityNotFoundException;
import com.jeremias.beprepared.models.City;
import com.jeremias.beprepared.repositories.CityRepository;
import com.jeremias.beprepared.services.CityService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;

    @Override
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    @Override
    public List<City> getCitiesByProvinceId(@NonNull Long provinceId) {
        return cityRepository.findAllByProvinceId(provinceId).orElseThrow(() -> new EntityNotFoundException("Cities not found with such id!"));
    }

    @Override
    public City getCityById(Long id) {
        return cityRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("City not found with such id!"));
    }
}
