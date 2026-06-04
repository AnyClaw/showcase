package com.example.showcase.dto.response;
public record UserBriefDTO(
        Integer userId,
        String firstName,
        String lastName,
        String middleName,
        String email,
        String role,
        Integer groupId,
        String groupName
) {}
