package com.example.showcase.mapper;

import com.example.showcase.dto.response.TeamDTO;
import com.example.showcase.dto.response.TeamMemberDTO;
import com.example.showcase.entity.Team;

import java.util.Map;

public class TeamMapper {

    public static TeamDTO toDto(Team team, Boolean currentUserIsLeader, Map<Integer, Boolean> leaderMap) {
        return new TeamDTO(
                team.getId(),
                team.getName(),
                team.getMembers().stream()
                        .map(u -> new TeamMemberDTO(
                                u.getId(),
                                u.getFirstName(),
                                u.getLastName(),
                                u.getEmail(),
                                leaderMap.getOrDefault(u.getId(), false) // ⭐ подставляем статус
                        ))
                        .toList()
        );
    }
}
