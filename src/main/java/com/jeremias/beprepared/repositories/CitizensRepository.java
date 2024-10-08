package com.jeremias.beprepared.repositories;

import com.jeremias.beprepared.models.Citizens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CitizensRepository extends JpaRepository<Citizens, Long> {
    List<Citizens> findAllByCityId(Long cityId);

    Optional<Citizens> findByOtp(String otp);

    Optional<Citizens> findByPhone(String phone);

    List<Citizens> findAllByCityProvinceId(Long provinceId);
}