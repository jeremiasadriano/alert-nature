package com.jeremias.beprepared.services;

import com.jeremias.beprepared.dto.request.AuthRequest;
import com.jeremias.beprepared.dto.response.AuthResponse;
import com.jeremias.beprepared.models.User;

public interface AuthService {
    AuthResponse login(AuthRequest authRequest);

    AuthResponse register(User user);
}
