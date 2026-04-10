package com.example.showcase.controller;

import com.example.showcase.dto.request.LoginRequestDTO;
import com.example.showcase.dto.request.RefreshTokenRequestDTO;
import com.example.showcase.dto.response.JwtResponseDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public interface AuthController {

    @PostMapping("/login")
    JwtResponseDTO authenticateUser(@RequestBody LoginRequestDTO loginRequest);

    @PostMapping("/refresh")
    JwtResponseDTO refreshAccessToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequest);

    @GetMapping("/validate")
    boolean validateToken(@RequestHeader("Authorization") String authHeader);
}
