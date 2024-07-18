package com.jeremias.beprepared.services;

import com.jeremias.beprepared.models.Province;

import java.util.List;

public interface ProvinceService {
    List<Province> getAllProvinces();

    Province getProvinceById(Long id);
}
