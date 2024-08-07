package com.jeremias.beprepared.security;

import com.jeremias.beprepared.exceptions.handlers.EntityBadRequestException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final AuthDetailsServiceImpls authDetailsServiceImpls;
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (extractToken(request).isEmpty()) {
            filterChain.doFilter(request, response);
            throw new EntityBadRequestException("The token cannot be null");
        }
        String token = extractToken(request).get();
        String username = this.jwtService.getUsername(token);
        if (this.jwtService.isValid(token, username)) {
            UserDetails userDetails = authDetailsServiceImpls.loadUserByUsername(username);
            var auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
    }

    private Optional<String> extractToken(HttpServletRequest request) {
        String tokenPrefix = "Bearer ";
        String token = request.getHeader("Authorization");
        if (StringUtils.hasText(token) && token.startsWith(tokenPrefix)) {
            return Optional.of(token.replace(tokenPrefix, ""));
        }
        return Optional.empty();
    }
}
