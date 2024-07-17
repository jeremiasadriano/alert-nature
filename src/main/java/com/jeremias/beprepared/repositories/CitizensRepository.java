package com.jeremias.beprepared.repositories;

import com.jeremias.beprepared.models.Citizens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitizensRepository extends JpaRepository<Citizens, Long> {
}
