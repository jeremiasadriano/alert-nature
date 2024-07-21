package com.jeremias.beprepared.repositories;

import com.jeremias.beprepared.models.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {
    List<Alert> findAllByStatus(Boolean status);

    List<Alert> findAllByCityId(Long cityId);

    List<Alert> findAllByCityIdAndStatus(Long cityId, Boolean status);

    List<Alert> findAllByProvinceId(Long provinceId);

    List<Alert> findAllByProvinceIdAndStatus(Long provinceId, Boolean status);
}
