package com.example.showcase.controller;

import com.example.showcase.dto.request.LoginRequestDTO;
import com.example.showcase.dto.request.RefreshTokenRequestDTO;
import com.example.showcase.dto.response.JwtResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public interface AuthController {

    @PostMapping("/login")
    @Operation(
            summary = "Аутентификация пользователя",
            description = """
                    Аутентифицирует пользователя по логину и паролю.
                    При успешной аутентификации возвращает access и refresh токены.
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешная аутентификация",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                        "accessToken": "eyJhbGciOiJIUzUxMiJ9...",
                                        "refreshToken": "eyJhbGciOiJIUzUxMiJ9...",
                                        "expiresIn": 900
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Ошибка валидации",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                        {
                                            "timestamp": "2026-05-03T15:17:19.601769",
                                            "status": 400,
                                            "error": "Bad Request",
                                            "message": "Incorrect params in request body: [, username: не должно быть пустым]",
                                            "code": "VALIDATION_ERROR",
                                            "path": "/api/auth/login"
                                        }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Неверный логин или пароль",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                        "timestamp": "2026-05-03T15:17:34.6742762",
                                        "status": 401,
                                        "error": "Unauthorized",
                                        "message": "Incorrect login or password",
                                        "code": "INVALID_CREDENTIALS",
                                        "path": "/api/auth/login"
                                    }
                                    """)
                    )
            )
    })
    JwtResponseDTO authenticateUser(
            @Valid @RequestBody LoginRequestDTO loginRequest
    );

    @PostMapping("/refresh")
    @Operation(
            summary = "Обновление access токена",
            description = """
                    Получение нового access токена по валидному refresh токену.
                    **Использование:**
                    1. Когда access токен истёк, клиент получает ошибку TOKEN_EXPIRED
                    2. Клиент отправляет refresh токен на этот эндпоинт
                    3. Сервер возвращает новый access токен
                    4. Клиент повторяет исходный запрос с новым токеном
                    
                    **Примечание:** Refresh токен остаётся прежним (не ротируется).
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Новый access токен успешно сгенерирован",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject("""
                                    {
                                        "accessToken": "eyJhbGciOiJIUzUxMiJ9...",
                                        "refreshToken": "eyJhbGciOiJIUzUxMiJ9...",
                                        "expiresIn": 900
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Refresh токен не представлен",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject("""
                                    {
                                        "timestamp": "2026-05-03T15:19:26.4979394",
                                        "status": 400,
                                        "error": "Bad Request",
                                        "message": "Incorrect params in request body: [, refreshToken: не должно быть пустым]",
                                        "code": "VALIDATION_ERROR",
                                        "path": "/api/auth/refresh"
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Refresh токен невалиден или истек",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject("""
                                    {
                                        "timestamp": "2026-05-03T15:20:15.3765931",
                                        "status": 401,
                                        "error": "Unauthorized",
                                        "message": "Refresh token is invalid or expired",
                                        "code": "TOKEN_EXPIRED",
                                        "path": "/api/auth/refresh"
                                    }
                                    """)
                    )
            ),

    })
    JwtResponseDTO refreshAccessToken(
            @Valid @RequestBody RefreshTokenRequestDTO refreshTokenRequest
    );

    @GetMapping("/validate")
    boolean validateToken(
            @RequestHeader("Authorization") String authHeader
    );
}
