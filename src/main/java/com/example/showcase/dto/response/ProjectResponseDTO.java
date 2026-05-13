package com.example.showcase.dto.response;

import com.example.showcase.enums.ProjectStatus;

public record ProjectResponseDTO(
        Integer id,
        String title,
        String target,
        String barrier,
        String existingSolution,
        String department,
        String projectType,
        ProjectStatus status,
        UserResponseDTO owner
) { }
