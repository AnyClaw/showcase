package com.example.showcase.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequestDTO(

        @NotBlank
        String username,

        @NotBlank
        @Size(min = 4)
        String password
) { }