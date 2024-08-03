package com.jeremias.beprepared.controllers;

import com.jeremias.beprepared.dto.request.UserRequest;
import com.jeremias.beprepared.dto.response.UserResponse;
import com.jeremias.beprepared.dto.response.UserStatsResponse;
import com.jeremias.beprepared.models.User;
import com.jeremias.beprepared.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final ModelMapper modelMapper;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<String> createUser(@Valid @RequestBody UserRequest userRequest) {
        return new ResponseEntity<>(userService.createUser(modelMapper.map(userRequest, User.class)), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(modelMapper.map(userService.getUserById(id), UserResponse.class));
    }

    @GetMapping("/metrics")
    public ResponseEntity<UserStatsResponse> getAllStats(@RequestParam(defaultValue = "true", name = "s") Boolean status) {
        return ResponseEntity.ok(userService.getAllStats(status));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@Valid @RequestBody UserRequest userRequest, @PathVariable("id") Long userId) {
        return ResponseEntity.ok(userService.updateUser(modelMapper.map(userRequest, User.class), userId));
    }
}


