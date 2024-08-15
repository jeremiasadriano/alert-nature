package com.jeremias.beprepared.repositories;

import com.jeremias.beprepared.models.UsersAccountInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersAccountInfoRepository extends JpaRepository<UsersAccountInfo, Long> {
    Optional<UsersAccountInfo> findByUserName(String name);
}
