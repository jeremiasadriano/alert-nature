package com.jeremias.beprepared.services.impl;

import com.jeremias.beprepared.exceptions.handlers.EntityBadRequestException;
import com.jeremias.beprepared.exceptions.handlers.EntityNotFoundException;
import com.jeremias.beprepared.models.Citizens;
import com.jeremias.beprepared.repositories.CitizensRepository;
import com.jeremias.beprepared.services.CitizensService;
import com.jeremias.beprepared.services.CityService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class CitizensServiceImpl implements CitizensService {
    private final CitizensRepository citizensRepository;
    private final CityService cityService;

    @Override
    @Transactional
    public String createCitizens(Citizens citizens, Long cityId) {
        if (!Objects.nonNull(citizens)) throw new EntityBadRequestException("Citizens data cannot be null!");
        var city = this.cityService.getCityById(cityId);
        citizens.setCity(city);
        citizens.setVerified(false);
        citizens.setOtp(optGenerator());
        Citizens citizen = this.citizensRepository.save(citizens);
        return "Citizen created successful, Your OTP is: " + citizen.getOtp();
    }

    @Override
    public List<Citizens> getAllCitizens() {
        return this.citizensRepository.findAll();
    }

    @Override
    public List<Citizens> getAllCitizensByCityId(Long cityId) {
        return this.citizensRepository.findAllByCityId(cityId);
    }

    @Override
    public List<Citizens> getAllCitizensByProvinceId(Long provinceId) {
        return this.citizensRepository.findAllByCityProvinceId(provinceId);
    }

    @Override
    public Citizens getCitizenById(Long id) {
        return this.citizensRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Citizens not found!"));
    }

    @Override
    @Transactional
    public String verifyAccount(String otp) {
        Citizens citizens = this.citizensRepository.findByOtp(otp).orElseThrow(() -> new EntityNotFoundException("Citizens not found!"));
        if (citizens.getOtpDuration().isBefore(LocalDateTime.now())) {
            log.error("Error, opt is invalid");
            throw new EntityBadRequestException("Otp is invalid Your can renew using: http://localhost:8080/api/v1/citizens/otp/renew?device=".concat(citizens.getDeviceId()));
        }
        citizens.setOtp(null);
        citizens.setVerified(true);
        this.citizensRepository.save(citizens);
        return "Citizen verified!";
    }

    @Override
    @Transactional
    public String renewOtp(String deviceId) {
        Citizens citizens = this.citizensRepository.findByDeviceId(deviceId).orElseThrow(() -> new EntityNotFoundException("Citizens not found!"));
        citizens.setOtp(optGenerator());
        citizens.setOtpDuration(LocalDateTime.now().plusMinutes(10L));
        Citizens citizen = this.citizensRepository.save(citizens);
        return "Your new Otp is: " + citizen.getOtp();
    }

    @NonNull
    private static String optGenerator() {
        char[] chars = new char[6];
        for (int i = 0; i < chars.length; i++) {
            int randomNumbers = new Random().nextInt(9);
            chars[i] = Integer.toString(randomNumbers).toCharArray()[0];
        }
        return new String(chars).trim();
    }
}
