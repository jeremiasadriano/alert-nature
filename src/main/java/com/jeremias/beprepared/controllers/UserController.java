package com.jeremias.beprepared.controllers;

import com.jeremias.beprepared.dto.request.UserRequest;
import com.jeremias.beprepared.dto.response.UserResponse;
import com.jeremias.beprepared.dto.response.UserStatsResponse;
import com.jeremias.beprepared.models.User;
import com.jeremias.beprepared.models.UserDetailsImpl;
import com.jeremias.beprepared.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "2. User Controller")
public class UserController {
    private final ModelMapper modelMapper;
    private final UserService userService;

    @GetMapping("/")
    public ResponseEntity<UserResponse> getPrincipal(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(modelMapper.map(userService.getPrincipal(userDetails), UserResponse.class));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(modelMapper.map(userService.getUserById(id), UserResponse.class));
    }

    @GetMapping("/metrics")
    public ResponseEntity<UserStatsResponse> getAllStats(@RequestParam(defaultValue = "true", name = "s") Boolean status) {
        return ResponseEntity.ok(userService.getAllStats(status));
    }

    @PutMapping("/")
    public ResponseEntity<String> updateUser(@Valid @RequestBody UserRequest userRequest, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(userService.updateUser(modelMapper.map(userRequest, User.class), userDetails));
    }

    @DeleteMapping("/")
    public ResponseEntity<String> deleteUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        this.userService.deleteUser(userDetails);
        return new ResponseEntity<>("User deleted!", HttpStatus.NO_CONTENT);
    }
}


