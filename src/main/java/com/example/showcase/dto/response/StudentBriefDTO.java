package com.example.showcase.dto.response;

public record StudentBriefDTO(
        Integer id,
        String firstName,
        String lastName,
        String middleName,
        String email
) {}
