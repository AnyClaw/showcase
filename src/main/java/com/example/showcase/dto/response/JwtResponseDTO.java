package com.example.showcase.dto.response;

public record JwtResponseDTO(
        String accessToken,
        String refreshToken,
        Long expiresIn
) { }