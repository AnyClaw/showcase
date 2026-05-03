package com.example.showcase.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtUtils {

    private final JwtConfig jwtConfig;
    private final SecretKey key;

    public JwtUtils(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
        this.key = Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    // для авторизации
    public String generateAccessTokenFromAuthentication(Authentication authentication, Long expiresInSeconds) {
        UserDetails user = (UserDetails) authentication.getPrincipal();
        return generateToken(user.getUsername(), expiresInSeconds * 1000, serializeRole(user));
    }

    // для обновления
    public String generateAccessTokenFromCredentials(String username, String role, Long expiresInSeconds) {
        return generateToken(username, expiresInSeconds * 1000, role);
    }

    public String generateRefreshToken(String username) {
        return generateToken(username, jwtConfig.getRefreshExpiration(), null);
    }

    private String generateToken(String username, long expirationMillis, String role) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationMillis);

        return Jwts.builder()
                .subject(username)           // идентификатор
                .issuedAt(now)               // время создания
                .expiration(expiration)      // время жизни
                .signWith(key)
                .claim("role", role)
                .compact();
    }

    // упаковка в токен
    public String serializeRole(UserDetails userDetails) {
        return userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // распаковка из токена
    // TODO: рассмотреть удаление метода
    public String getRoleFromToken(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // полная валидация с пользователем
    // используется в фильтре при каждом запросе
    // TODO: рассмотреть возможность ограничится базовой валидацией
    public boolean validateAccessToken(String token, UserDetails userDetails) {
        try {
            String username = extractUsername(token);
            boolean isValid = username.equals(userDetails.getUsername()) && !isTokenExpired(token);

            if (!isValid) {
                log.warn("Токен невалиден для пользователя: {}", username);
            }
            return isValid;

        } catch (ExpiredJwtException e) {
            log.error("Токен истёк: {}", e.getMessage());
        } catch (JwtException e) {
            log.error("Ошибка валидации токена: {}", e.getMessage());
        }
        return false;
    }

    // базовая валидация без пользователя
    public boolean isTokenValid(String token) {
        try {
            extractAllClaims(token); // мб убрать
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }
}