package com.example.showcase.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtUtils {

    private final JwtConfig jwtConfig;
    private final Key key;

    public JwtUtils(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
        this.key = Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes());
    }

    // для авторизации
    public String generateAccessTokenFromAuthentication(Authentication authentication, Long expiresInSeconds) {
        UserDetails user = (UserDetails) authentication.getPrincipal();
        return generateToken(user.getUsername(), expiresInSeconds * 1000, serializeRoles(user));
    }

    // для обновления
    public String generateAccessTokenFromCredentials(String username, String roles, Long expiresInSeconds) {
        return generateToken(username, expiresInSeconds * 1000, roles);
    }

    public String generateRefreshToken(String username) {
        return generateToken(username, jwtConfig.getRefreshExpiration(), null);
    }

    private String generateToken(String username, long expirationMillis, String roles) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationMillis);

        return Jwts.builder()
                .setSubject(username)           // идентификатор
                .setIssuedAt(now)               // время создания
                .setExpiration(expiration)      // время жизни
                .signWith(key, SignatureAlgorithm.HS512)
                .claim("roles", roles)
                .compact();
    }

    // упаковка в токен
    private String serializeRoles(UserDetails userDetails) {
        return userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // распаковка из токена
    public String getRolesFromToken(String token) {
        return extractAllClaims(token).get("roles", String.class);
    }

    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
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
            extractAllClaims(token);
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }
}