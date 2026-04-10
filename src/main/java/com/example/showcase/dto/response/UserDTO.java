package com.example.showcase.dto.response;

import com.example.showcase.enums.UserRoleName;

public record UserDTO(
        Integer id,
        String firstName,
        String lastName,
        String middleName,
        String phoneNumber,
        String email,
        UserRoleName role
){}
