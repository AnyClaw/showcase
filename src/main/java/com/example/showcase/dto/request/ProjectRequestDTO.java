package com.example.showcase.dto.request;

public record ProjectRequestDTO(
        String title,
        String target,
        String barrier,
        String existingSolution,
        String department,
        String projectType
) {
}
