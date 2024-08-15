package com.jeremias.beprepared.services;

import com.jeremias.beprepared.dto.response.UserStatsResponse;
import com.jeremias.beprepared.models.User;
import com.jeremias.beprepared.models.UserDetailsImpl;

public interface UserService {
    void createUser(User user);

    User getPrincipal(UserDetailsImpl id);

    User getUserById(Long id);

    UserStatsResponse getAllStats(Boolean status);

    String updateUser(User user, UserDetailsImpl username);

    void deleteUser(UserDetailsImpl userDetails);
}
