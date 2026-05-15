package com.example.showcase.dto.response;

public record TeamMemberDTO (
    Integer id,
    String firstName,
    String lastName,
    String email,
    Boolean isLeader
) {}
