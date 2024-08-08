package com.jeremias.beprepared.services.impl;

import com.jeremias.beprepared.dto.request.AuthRequest;
import com.jeremias.beprepared.dto.response.AuthResponse;
import com.jeremias.beprepared.exceptions.handlers.EntityNotFoundException;
import com.jeremias.beprepared.models.User;
import com.jeremias.beprepared.models.enums.Roles;
import com.jeremias.beprepared.security.JwtService;
import com.jeremias.beprepared.services.AuthService;
import com.jeremias.beprepared.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Override
    @Transactional(readOnly = true)
    public AuthResponse login(AuthRequest authRequest) {
        var auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.email(), authRequest.password()));
        if (!auth.isAuthenticated()) throw new EntityNotFoundException("Invalid password or email!");
        return new AuthResponse(jwtService.generateToken(authRequest.email(), new HashMap<>()));
    }

    @Override
    public AuthResponse register(User user) {
        var userData = User.builder()
                .name(user.getName())
                .password(passwordEncoder.encode(user.getPassword()))
                .email(user.getEmail())
                .roles(Roles.USER)
                .build();
        userService.createUser(userData);
        return new AuthResponse(jwtService.generateToken(user.getEmail(), new HashMap<>()));
    }
}