package com.example.showcase.controller.impls;

import com.example.showcase.controller.AuthController;
import com.example.showcase.dto.request.LoginRequestDTO;
import com.example.showcase.dto.request.RefreshTokenRequestDTO;
import com.example.showcase.dto.response.JwtResponseDTO;
import com.example.showcase.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;

    @Override
    public JwtResponseDTO authenticateUser(LoginRequestDTO loginRequest) {
        log.info("Получен запрос на аутентификацию пользователя: {}", loginRequest.username());
        return authService.authenticateUser(loginRequest);
    }

    @Override
    public JwtResponseDTO refreshAccessToken(RefreshTokenRequestDTO refreshTokenRequest) {
        log.info("Получен запрос на обновление токена");
        return authService.refreshAccessToken(refreshTokenRequest);
    }

    @Override
    public boolean validateToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            return authService.validateToken(token);
        }

        return false;
    }
}
