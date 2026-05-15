package com.example.showcase.service;

import com.example.showcase.dto.response.TeamDTO;
import com.example.showcase.dto.response.TeamMemberDTO;
import com.example.showcase.entity.Team;
import com.example.showcase.exception.TeamNotFoundException;
import com.example.showcase.exception.UserNotFoundException;
import com.example.showcase.mapper.TeamMapper;
import com.example.showcase.repository.TeamRepository;
import com.example.showcase.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public TeamDTO getMyTeam(Integer userId) {
        List<Object[]> rows = teamRepository.getFullTeamData(userId);

        if (rows.isEmpty()) {
            throw new TeamNotFoundException(userId);
        }
        Integer teamId = (Integer) rows.get(0)[0];
        String teamName = (String) rows.get(0)[1];

        List<TeamMemberDTO> members = new ArrayList<>(rows.size());
        for (Object[] row : rows) {
            members.add(new TeamMemberDTO(
                    (Integer) row[2],   // user_id
                    (String) row[3],    // first_name
                    (String) row[4],    // last_name
                    (String) row[5],    // email
                    (Boolean) row[6]    // is_leader
            ));
        }

        return new TeamDTO(teamId, teamName, members);
    }

    @Transactional
    public void leaveTeam(int userId, Integer newLeaderId) {

        Team team = teamRepository.findWithMembersByUserId(userId)
                .orElseThrow(() -> new TeamNotFoundException("Вы не состоите ни в одной команде"));

        int memberCount = teamRepository.countMembersByTeamId(team.getId());
        boolean isCurrentUserLeader = teamRepository.isUserLeader(team.getId(), userId);

        if (isCurrentUserLeader) {
            if (memberCount == 1) {
                log.info("Лидер покидает команду, так как остался единственным участником");
            } else {
                if (newLeaderId == null) {
                    throw new IllegalArgumentException("Вы являетесь лидером. Перед уходом назначьте нового лидера.");
                }
                if (userId == newLeaderId) {
                    throw new IllegalArgumentException("Нельзя передать лидерство самому себе.");
                }

                if (!(teamRepository.countMemberInTeam(team.getId(), newLeaderId)>0)) {
                    throw new IllegalArgumentException("Новый лидер должен быть действующим участником команды.");
                }

                teamRepository.clearLeaderForTeam(team.getId());
                teamRepository.setLeaderForTeam(team.getId(), newLeaderId);
                log.info("Лидерство передано пользователю id={}", newLeaderId);
            }
        } else {
            if (newLeaderId != null) {
                log.warn("Пользователь не является лидером");
            }
        }

        teamRepository.removeMemberFromTeam(team.getId(), userId);
        log.info("Пользователь id={} покинул команду id={}", userId, team.getId());
    }

    @Transactional
    public void excludeUser(Integer leaderId, int targetUserId) {

        Team team = teamRepository.findByMembersId(leaderId)
                .orElseThrow(() -> new TeamNotFoundException("Команда не найдена"));

        if (!teamRepository.isUserLeader(team.getId(),leaderId)){
            throw new IllegalArgumentException("Вы не являетесь лидером команды.");
        }
        if (leaderId == targetUserId) {
            throw new IllegalArgumentException("Лидер не может исключить сам себя.");
        }

        if (teamRepository.countMemberInTeam(team.getId(), targetUserId) == 0) {
            throw new IllegalArgumentException("Указанный пользователь не состоит в вашей команде.");
        }

        teamRepository.removeMemberFromTeam(team.getId(), targetUserId);
        log.info("Лидер id={} исключил пользователя id={} из команды id={}", leaderId, targetUserId, team.getId());
    }

    @Transactional
    public void changeTeamLeader(int currentLeaderId, int newLeaderId) {
        Team team = teamRepository.findByMembersId(currentLeaderId)
                .orElseThrow(() -> new TeamNotFoundException("Команда не найдена"));

        if (!teamRepository.isUserLeader(team.getId(),currentLeaderId)){
            throw new IllegalArgumentException("Вы не являетесь лидером команды.");
        }
        if (currentLeaderId== newLeaderId) {
            throw new IllegalArgumentException("Нельзя назначить лидером самого себя.");
        }

        if (teamRepository.countMemberInTeam(team.getId(), newLeaderId) == 0) {
            throw new IllegalArgumentException("Новый лидер должен быть участником вашей команды.");
        }

        teamRepository.clearLeaderForTeam(team.getId());
        teamRepository.setLeaderForTeam(team.getId(), newLeaderId);
        log.info("Лидерство передано пользователю id={}", newLeaderId);

        log.info("Лидер команды id={} изменён: {} → {}", team.getId(), currentLeaderId, newLeaderId);
    }
    // DEMO
    @Transactional
    public void inviteUserToTeam(int leaderId, int targetUserId) {
        Team team = teamRepository.findByMembersId(leaderId)
                .orElseThrow(() -> new TeamNotFoundException("Команда не найдена"));

        if (!teamRepository.isUserLeader(team.getId(),leaderId)){
            throw new IllegalArgumentException("Вы не являетесь лидером команды.");
        }
        if (leaderId== targetUserId) {
            throw new IllegalArgumentException("Нельзя пригласить самого себя.");
        }

        if (!userRepository.existsById(targetUserId)) {
            throw new UserNotFoundException("Пользователь с id " + targetUserId + " не найден");
        }

        if (teamRepository.isUserInAnyTeam(targetUserId)) {
            throw new IllegalArgumentException("Пользователь уже состоит в команде и не может быть приглашён");
        }

        teamRepository.addMemberToTeam(team.getId(), targetUserId);

        log.info("Лидер id={} пригласил пользователя id={} в команду id={}", leaderId, targetUserId, team.getId());
    }


}