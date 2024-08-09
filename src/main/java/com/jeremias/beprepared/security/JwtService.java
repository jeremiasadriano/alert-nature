package com.jeremias.beprepared.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {
    @Value("${app.secret.key}")
    private String SECRET_KEY;
    @Value("${app.secret.expiration-time}")
    private long EXPIRATION_TIME;


    private SecretKey key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(SECRET_KEY));
    }

    private Date expirationDate() {
        return Date.from(Instant.now().plusMillis(EXPIRATION_TIME));
    }

    private Date getNowDate() {
        return Date.from(Instant.now());
    }

    public String generateToken(String username, Map<String, Object> claims) {
        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .expiration(expirationDate())
                .issuedAt(getNowDate())
                .signWith(key())
                .compact();
    }


    public Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(key())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    public boolean isValid(String token, String username) {
        return (getUsername(token).equals(username) && !isExpired(token));
    }

    private boolean isExpired(String token) {
        return getClaims(token).getExpiration().before(getNowDate());
    }
}