package com.jeremias.beprepared.security;

import com.jeremias.beprepared.exceptions.handlers.EntityBadRequestException;
import com.jeremias.beprepared.exceptions.handlers.EntityNotFoundException;
import com.jeremias.beprepared.models.UserDetailsImpl;
import com.jeremias.beprepared.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpls implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userDetails = userRepository.findByEmail(username)
                .map(UserDetailsImpl::new)
                .orElseThrow(() -> new EntityNotFoundException("User not found with such email"));
        if (userDetails.isEnabled()) return userDetails;
        throw new EntityBadRequestException("The account isn't enabled");
    }
}