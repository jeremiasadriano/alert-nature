package com.jeremias.beprepared.controllers;

import com.jeremias.beprepared.dto.request.AuthRequest;
import com.jeremias.beprepared.dto.request.UserRequest;
import com.jeremias.beprepared.dto.response.AuthResponse;
import com.jeremias.beprepared.models.User;
import com.jeremias.beprepared.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "1. Auth Controller")
public class AuthController {
    private final AuthService authService;
    private final ModelMapper modelMapper;

    @PostMapping("/login")
    @Operation(summary = "Login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(authService.login(authRequest));
    }

    @PostMapping("/register")
    @Operation(summary = "Sign up")
    public ResponseEntity<String> createUser(@Valid @RequestBody UserRequest userRequest) {
        return new ResponseEntity<>(authService.register(modelMapper.map(userRequest, User.class)), HttpStatus.CREATED);
    }

    @GetMapping("/active")
    public ResponseEntity<String> activeAccount(@RequestParam String token) {
        return ResponseEntity.ok(authService.activeUser(token));
    }

    @GetMapping("/reactive")
    public ResponseEntity<String> reactivationToken(@RequestParam String token) {
        return ResponseEntity.ok(authService.reactivationToken(token));
    }
}
