package com.example.showcase.dto.response;

import com.example.showcase.enums.ProjectStatus;

public record ProjectBriefDTO(
        Integer projectId,
        String title,
        String target,
        String department,
        String projectType,
        String projectStatus
) {
}
