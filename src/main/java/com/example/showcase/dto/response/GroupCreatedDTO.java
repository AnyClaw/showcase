package com.example.showcase.dto.response;


public record GroupCreatedDTO(
        Integer id,
        String groupName,
        Integer teacherId
) {}
