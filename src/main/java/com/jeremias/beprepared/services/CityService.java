package com.jeremias.beprepared.services;

import com.jeremias.beprepared.models.City;
import com.jeremias.beprepared.models.Province;

import java.util.List;

public interface CityService {
    List<City> getAllCities();

    List<City> getCitiesByProvinceId(Long provinceId);

    City getCityById(Long id);
}
