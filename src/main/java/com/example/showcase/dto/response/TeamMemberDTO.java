package com.example.showcase.dto.response;

import java.time.OffsetDateTime;

public record TeamMemberDTO (
    Integer id,
    String firstName,
    String lastName,
    String email,
    Boolean isLeader,
    java.time.Instant joinedAt,
    java.time.Instant leftAt
) {}
