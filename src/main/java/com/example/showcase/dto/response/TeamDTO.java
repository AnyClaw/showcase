package com.example.showcase.dto.response;

import java.util.List;

public record TeamDTO (
    Integer id,
    String name,
    List<TeamMemberDTO> members
){}
