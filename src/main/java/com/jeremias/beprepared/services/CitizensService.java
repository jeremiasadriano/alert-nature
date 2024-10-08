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

    String renewOtp(String deviceId);

    Citizens updateAccount(Citizens citizens, String phone);

    String deleteAccount(String deviceNumber);

    void confirmAccountDeletion(String otp, String deviceNumber);
}
