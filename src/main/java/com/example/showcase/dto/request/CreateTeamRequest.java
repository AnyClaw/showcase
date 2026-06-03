package com.example.showcase.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateTeamRequest(
        @NotBlank(message = "Название команды не может быть пустым")
        @Size(min = 3, max = 100, message = "Название должно быть от 3 до 100 символов")
        String name
) {}
