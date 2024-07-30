package com.jeremias.beprepared.services;

import com.jeremias.beprepared.models.Citizens;

import java.util.List;

public interface CitizensService {
    String createCitizens(Citizens citizens, Long cityId);

    List<Citizens> getAllCitizens();

    List<Citizens> getAllCitizensByCityId(Long cityId);

    List<Citizens> getAllCitizensByProvinceId(Long provinceId);

    Citizens getCitizenById(Long id);

    String verifyAccount(String otp);
}
