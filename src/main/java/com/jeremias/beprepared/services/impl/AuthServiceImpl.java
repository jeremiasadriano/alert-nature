package com.jeremias.beprepared.services.impl;

import com.jeremias.beprepared.dto.request.AuthRequest;
import com.jeremias.beprepared.dto.response.AuthResponse;
import com.jeremias.beprepared.exceptions.handlers.EntityBadRequestException;
import com.jeremias.beprepared.exceptions.handlers.EntityNotFoundException;
import com.jeremias.beprepared.infra.EmailSender;
import com.jeremias.beprepared.models.User;
import com.jeremias.beprepared.models.UsersAccountInfo;
import com.jeremias.beprepared.models.enums.Roles;
import com.jeremias.beprepared.repositories.UserRepository;
import com.jeremias.beprepared.repositories.UsersAccountInfoRepository;
import com.jeremias.beprepared.security.JwtService;
import com.jeremias.beprepared.security.UsersAccountsInfoToken;
import com.jeremias.beprepared.services.AuthService;
import com.jeremias.beprepared.services.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    private final UserRepository userRepository;
    private final EmailSender emailSender;
    private final UsersAccountInfoRepository usersAccountRepository;
    private final UsersAccountsInfoToken usersAccountsInfoToken;

    @Override
    @Transactional(readOnly = true)
    public AuthResponse login(AuthRequest authRequest) {
        var auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.email(), authRequest.password()));
        if (!auth.isAuthenticated()) throw new EntityNotFoundException("Invalid password or email!");
        return new AuthResponse(jwtService.generateToken(authRequest.email(), new HashMap<>()));
    }

    @Override
    public String register(User user) {
        var userData = User.builder()
                .name(user.getName())
                .password(passwordEncoder.encode(user.getPassword()))
                .email(user.getEmail())
                .roles(Roles.USER)
                .build();
        userService.createUser(userData);

        UsersAccountInfo accountInfo = UsersAccountInfo.builder()
                .token(usersAccountsInfoToken.generateToken(user.getName()))
                .expirationToken(LocalDateTime.now().plusMinutes(10L))
                .user(userData)
                .build();
        usersAccountRepository.save(accountInfo);

        String url = "http://localhost:8080/api/v1/auth/active?token=".concat(accountInfo.getToken());
        emailSender.send("Account Activation", user.getEmail(), "To active your account click here ".concat(url), "");
        return "Account created successful, please check your email";
    }

    @Override
    public String activeUser(String token) {
        var accountInfo = extractUser(token);
        if (accountInfo.getExpirationToken().isBefore(LocalDateTime.now())) {
            throw new EntityBadRequestException("Token has already expired");
        }
        User user = userService.getUserById(accountInfo.getUser().getId());
        user.setActive(true);
        userRepository.save(user);
        return "Account activated";
    }

    @Override
    public String reactivationToken(String token) {
        var accountInfo = extractUser(token);
        accountInfo.setToken(usersAccountsInfoToken.generateToken(accountInfo.getUser().getName()));
        accountInfo.setExpirationToken(LocalDateTime.now().plusMinutes(10L));
        accountInfo.setUser(accountInfo.getUser());
        usersAccountRepository.save(accountInfo);
        String url = "http://localhost:8080/api/v1/auth/active?token=".concat(accountInfo.getToken());
        emailSender.send("Account Activation", accountInfo.getUser().getEmail(), "To active your account click here ".concat(url), "");
        return "Open your email inbox!";
    }

    @NonNull
    private UsersAccountInfo extractUser(String token) {
        String username = usersAccountsInfoToken.getUsername(token);
        return usersAccountRepository.findByUserName(username).orElseThrow(() -> new EntityBadRequestException("URL data invalid"));
    }
}