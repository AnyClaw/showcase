package com.example.showcase.dto.response;

import java.time.OffsetDateTime;

public record TeamQueryResult(
        Integer teamId,
        String teamName,
        Integer userId,
        String firstName,
        String lastName,
        String email,
        Boolean isLeader,
        java.time.Instant joinedAt,
        java.time.Instant leftAt
) {}
