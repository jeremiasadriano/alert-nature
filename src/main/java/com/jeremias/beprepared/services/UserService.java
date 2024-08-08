package com.jeremias.beprepared.services;

import com.jeremias.beprepared.dto.response.UserStatsResponse;
import com.jeremias.beprepared.models.User;

public interface UserService {
    void createUser(User user);

    User getUserById(Long id);

    UserStatsResponse getAllStats(Boolean status);

    String updateUser(User user, Long userId);

    User getUserByEmail(String email);
}
