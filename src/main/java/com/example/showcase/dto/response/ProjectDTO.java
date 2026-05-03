package com.example.showcase.dto.response;

import com.example.showcase.enums.ProjectStatusName;

public record ProjectDTO(
        Integer id,
        String title,
        String target,
        String barrier,
        String existingSolution,
        String department,
        ProjectStatusName status,
        UserDTO owner
) { }
