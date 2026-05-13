package com.example.showcase.dto.response;

import com.example.showcase.enums.UserRole;

public record UserResponseDTO(
        Integer id,
        String firstName,
        String lastName,
        String middleName,
        String phoneNumber,
        String email,
        UserRole role
){}
