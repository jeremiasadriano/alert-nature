package com.jeremias.beprepared.services.impl;

import com.jeremias.beprepared.exceptions.handlers.EntityBadRequestException;
import com.jeremias.beprepared.exceptions.handlers.EntityNotFoundException;
import com.jeremias.beprepared.infra.SmsSender;
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
    private final SmsSender smsSender;

    @Override
    @Transactional
    public String createCitizens(Citizens citizens, Long cityId) {
        if (!Objects.nonNull(citizens)) throw new EntityBadRequestException("Citizens data cannot be null!");
        var city = this.cityService.getCityById(cityId);
        citizens.setCity(city);
        citizens.setVerified(false);
        citizens.setOtp(optGenerator());
        Citizens citizen = this.citizensRepository.save(citizens);
        smsSender.send(citizen.getPhone(), "Your confirmation code is: " + citizen.getOtp());
        return "Citizen created successful, please check your inbox";
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
        Citizens citizens = this.citizensRepository.findByOtp(otp).orElseThrow(() -> new EntityNotFoundException("Error, opt is invalid!"));
        if (citizens.getOtpDuration().isBefore(LocalDateTime.now())) {
            log.error("Error, opt has expired!");
            throw new EntityBadRequestException("Your Otp has expired, you can renew using: http://localhost:8080/api/v1/citizens/otp/" + citizens.getPhone() + "/renew");
        }
        citizens.setOtp(null);
        citizens.setVerified(true);
        this.citizensRepository.save(citizens);
        return "Citizen verified!";
    }

    @Override
    @Transactional
    public String renewOtp(String phone) {
        Citizens citizens = this.citizensRepository.findByPhone(phone).orElseThrow(() -> new EntityNotFoundException("Citizens not found!"));
        if (citizens.getOtp() == null) throw new EntityBadRequestException("The Citizens is already activated");
        citizens.setOtp(optGenerator());
        citizens.setOtpDuration(LocalDateTime.now().plusMinutes(10L));
        Citizens citizen = this.citizensRepository.save(citizens);
        smsSender.send(citizen.getPhone(), "Your new OTP is: " + citizen.getOtp());
        return "OTP renewed successful, please check your inbox";
    }

    @Override
    @Transactional
    public Citizens updateAccount(Citizens citizens, String phone) {
        var citizenData = getCitizensByPhoneNumber(phone);
        if (!citizens.getName().isEmpty()) citizenData.setName(citizens.getName());
        if (!citizens.getPhone().isEmpty()) citizenData.setPhone(citizens.getPhone());
        if (!citizens.getEmail().isEmpty()) citizenData.setEmail(citizens.getEmail());
        return this.citizensRepository.save(citizenData);
    }

    @Override
    @Transactional
    public String deleteAccount(String deviceNumber) {
        Citizens citizens = getCitizensByPhoneNumber(deviceNumber);
        citizens.setOtp(optGenerator());
        var citizen = this.citizensRepository.save(citizens);
        smsSender.send(citizens.getPhone(), "To delete your account you need to this otp: " + citizen.getOtp());
        return "Please check your inbox";
    }

    @Override
    @Transactional
    public void confirmAccountDeletion(String otp, String phone) {
        Citizens citizens = getCitizensByPhoneNumber(phone);
        if (citizens.getOtp().equals(otp))
            this.citizensRepository.delete(citizens);
        else
            throw new EntityBadRequestException("The otp inserted is invalid");
    }

    @NonNull
    private Citizens getCitizensByPhoneNumber(String phone) {
        return this.citizensRepository.findByPhone(phone)
                .orElseThrow(() -> new EntityBadRequestException("The citizen doesn't have an account registered"));
    }

    @NonNull
    private static String optGenerator() {
        StringBuilder otp = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            otp.append(new Random().nextInt(9));
        }
        return otp.toString();
    }
}
