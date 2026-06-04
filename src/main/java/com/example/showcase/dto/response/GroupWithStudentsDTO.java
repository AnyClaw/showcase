package com.example.showcase.dto.response;

import java.util.List;

public record GroupWithStudentsDTO(
        Integer groupId,
        String groupName,
        List<StudentBriefDTO> students
) {}