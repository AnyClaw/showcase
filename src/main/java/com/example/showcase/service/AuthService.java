package com.example.showcase.service;

import com.example.showcase.dto.request.LoginRequestDTO;
import com.example.showcase.dto.response.JwtResponseDTO;
import com.example.showcase.security.jwt.JwtConfig;
import com.example.showcase.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final JwtConfig jwtConfig;
    private final AuthUserDetailsService userDetailsService;

    // TODO: вынести обработку исключений BadCredentialsException в ExceptionHandler
    public JwtResponseDTO authenticateUser(LoginRequestDTO loginRequest) {
        log.debug("Попытка аутентификации пользователя: {}", loginRequest.username());

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginRequest.username(), loginRequest.password()
        );

        // AuthenticationManager вызовет:
        //   - CustomUserDetailsService.loadUserByUsername()
        //   - PasswordEncoder.matches() для проверки пароля
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Long expiresInSeconds = jwtConfig.getExpiration() / 1000;

        String accessToken = jwtUtils.generateAccessTokenFromAuthentication(authentication, expiresInSeconds);
        String refreshToken = jwtUtils.generateRefreshToken(authentication.getName());

        log.info("Пользователь {} успешно аутентифицирован. Access токен истечёт через {} секунд",
                loginRequest.username(), expiresInSeconds);

        return new JwtResponseDTO(accessToken, refreshToken, expiresInSeconds);
    }
}
