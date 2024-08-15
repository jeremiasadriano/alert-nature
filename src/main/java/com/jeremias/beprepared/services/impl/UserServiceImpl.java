package com.jeremias.beprepared.services.impl;

import com.jeremias.beprepared.dto.response.UserStatsResponse;
import com.jeremias.beprepared.exceptions.handlers.EntityBadRequestException;
import com.jeremias.beprepared.exceptions.handlers.EntityConflictException;
import com.jeremias.beprepared.exceptions.handlers.EntityNotFoundException;
import com.jeremias.beprepared.models.User;
import com.jeremias.beprepared.models.UserDetailsImpl;
import com.jeremias.beprepared.repositories.AlertRepository;
import com.jeremias.beprepared.repositories.CitizensRepository;
import com.jeremias.beprepared.repositories.UserRepository;
import com.jeremias.beprepared.services.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final CitizensRepository citizensRepository;
    private final AlertRepository alertRepository;

    @Override
    @Transactional
    public void createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail()))
            throw new EntityConflictException("User Already exist with such email!");
        this.userRepository.save(user);
    }

    @Override
    public User getPrincipal(UserDetailsImpl userDetails) {
        if (Objects.isNull(userDetails)) throw new EntityNotFoundException("User not found!");
        return this.userRepository.findByEmail(userDetails.getUsername()).orElseThrow(() -> new EntityNotFoundException("User not found!"));
    }

    @Override
    public User getUserById(Long id) {
        return this.userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found!"));
    }

    @Override
    public UserStatsResponse getAllStats(Boolean status) {
        return UserStatsResponse
                .builder()
                .citizens(this.citizensRepository.count())
                .totalAlerts(this.alertRepository.count())
                .activeAlerts(this.alertRepository.countAlertByStatus(status))
                .build();
    }

    @Override
    @Transactional
    public String updateUser(User user, UserDetailsImpl username) {
        User userData = this.getPrincipal(username);
        if (Objects.isNull(userData)) throw new EntityBadRequestException("The user cannot be null!");
        if (user.getName() != null) userData.setName(user.getName());
        if (user.getEmail() != null) userData.setEmail(user.getEmail());
        if (user.getPassword() != null) userData.setPassword(user.getPassword());
        this.userRepository.save(userData);
        return "User Updated!";
    }

    @Override
    public void deleteUser(UserDetailsImpl userDetails) {
        User user = this.getPrincipal(userDetails);
        this.userRepository.delete(user);
    }
}
