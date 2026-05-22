package com.example.showcase.service;

import com.example.showcase.dto.response.TeamDTO;
import com.example.showcase.dto.response.TeamMemberDTO;
import com.example.showcase.dto.response.TeamQueryResult;
import com.example.showcase.entity.Team;
import com.example.showcase.exception.*;
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
            if (userId == null) {
                throw new UserNotFoundException("User not authenticated");
            }
            Integer teamId =  teamRepository.findCurrentTeamIdByUserId(userId)
                .orElseThrow(() -> new TeamNotFoundException(userId));

            List<TeamQueryResult> results = teamRepository.findActiveMembersByTeamId(teamId);
            if (results.isEmpty()) {
                throw new TeamNotFoundException("Команда c ID " + teamId + " пуста или не найдена");
            }

            var first = results.get(0);
            List<TeamMemberDTO> members = results.stream()
                    .map(r -> new TeamMemberDTO(
                            r.userId(),
                            r.firstName(),
                            r.lastName(),
                            r.email(),
                            r.isLeader(),
                            r.joinedAt(),
                            r.leftAt()
                    ))
                    .toList();

            return new TeamDTO(first.teamId(), first.teamName(), members);
    }

    @Transactional
    public void leaveTeam(Integer userId, Integer newLeaderId) {
        if (userId == null) throw new IllegalArgumentException("ID не может быть нулевым");
        Integer teamId = teamRepository.findCurrentTeamIdByUserId(userId)
                .orElseThrow(() -> new TeamNotFoundException("Вы не состоите ни в одной команде"));
        Integer teamIdFromNew = teamRepository.findCurrentTeamIdByUserId(userId)
                .orElseThrow(() -> new TeamNotFoundException("Новый лидер не состоите ни в одной команде"));

        if (teamId!=teamIdFromNew){
            throw new LeaderException("Выбранный пользователь не состоит в вашей команде");}

        int activeCount = teamRepository.countActiveMembersByTeamId(teamId);
        boolean isLeader = teamRepository.isUserActiveLeader(teamId, userId).orElse(false);

        if (isLeader) {
            if (activeCount == 1) {
                log.info("Лидер под ID "+ userId +" покидает команду, так как остался единственным участником");
            } else {
                if (newLeaderId == null) {
                    throw new LeaderException("Вы являетесь лидером. Перед уходом назначьте нового лидера.");}
                if (userId == newLeaderId) {
                    throw new LeaderException("Нельзя передать лидерство самому себе.");}

                teamRepository.clearActiveLeader(teamId);
                teamRepository.setLeaderForTeam(teamId, newLeaderId);
                log.info("Лидерство в команде {} передано пользователю {} лидером {}", teamId, newLeaderId, userId);
            }
        } else {
            if (newLeaderId != null) {
                throw new LeaderException("Только лидер команды может назначать нового лидера.");
            }
        }

        teamRepository.markMemberAsLeft(teamId, userId);
        log.info("Пользователь под ID {} покинул команду c ID {}", userId, teamId);
    }

    @Transactional
    public void excludeUser(Integer leaderId, Integer targetUserId) {
        if (leaderId == null || targetUserId == null) {
            throw new IllegalArgumentException("ID пользователя не может быть null");
        }

        Integer teamId = teamRepository.findCurrentTeamIdByUserId(leaderId)
                .orElseThrow(() -> new TeamNotFoundException("Вы не состоите ни в одной команде"));
        Integer teamIdFromNew = teamRepository.findCurrentTeamIdByUserId(targetUserId)
                .orElseThrow(() -> new TeamNotFoundException("Новый лидер не состоите ни в одной команде"));

        if (!teamId.equals(teamIdFromNew)){
            throw new ExcludeException("Выбранный пользователь не состоит в вашей команде");}

        boolean isLeader = teamRepository.isUserActiveLeader(teamId, leaderId).orElse(false);
        if (!isLeader){
            throw new LeaderException("Только лидер команды может назначать нового лидера.");
        }

        if (leaderId.equals(targetUserId)) {
            throw new LeaderException("Лидер не может исключить сам себя.");
        }

        if (!teamRepository.isUserActiveRegularMember(teamId, targetUserId)) {
            throw new TeamNotFoundException("Указанный пользователь не состоит в вашей команде или уже покинул её.");
        }

        teamRepository.markMemberAsLeft(teamId, targetUserId);
        log.info("Лидер ID={} исключил пользователя ID={} из команды ID={}", leaderId, targetUserId, teamId);
    }

    @Transactional
    public void changeTeamLeader(Integer currentLeaderId, Integer newLeaderId) {
        if (currentLeaderId == null || newLeaderId == null) {
            throw new IllegalArgumentException("ID пользователя не может быть null");
        }

        Integer teamId = teamRepository.findCurrentTeamIdByUserId(currentLeaderId)
                .orElseThrow(() -> new TeamNotFoundException("Вы не состоите ни в одной команде"));
        Integer teamIdFromNew = teamRepository.findCurrentTeamIdByUserId(newLeaderId)
                .orElseThrow(() -> new TeamNotFoundException("Новый лидер не состоите ни в одной команде"));

        if (!teamId.equals(teamIdFromNew)){
            throw new ExcludeException("Выбранный пользователь не состоит в вашей команде");}

        boolean isLeader = teamRepository.isUserActiveLeader(teamId, currentLeaderId).orElse(false);
        if (!isLeader){
            throw new LeaderException("Только лидер команды может назначать нового лидера.");
        }

        if (newLeaderId.equals(currentLeaderId)) {
            throw new LeaderException("Вы уже лидер.");
        }

        teamRepository.clearActiveLeader(teamId);
        teamRepository.setLeaderForTeam(teamId, newLeaderId);
        log.info("Лидерство передано пользователю id={}", newLeaderId);

        log.info("Лидерство в команде ID={} передано: {} → {}", teamId, currentLeaderId, newLeaderId);
    }
    // DEMO
    @Transactional
    public void inviteUserToTeam(Integer leaderId, Integer targetUserId) {
        if (leaderId == null || targetUserId == null) {
            throw new IllegalArgumentException("ID пользователя не может быть null");
        }
        Integer teamId = teamRepository.findCurrentTeamIdByUserId(leaderId)
                .orElseThrow(() -> new TeamNotFoundException("Вы не состоите ни в одной команде"));

        if (!teamRepository.isUserActiveLeader(teamId, leaderId).orElse(false)) {
            throw new LeaderException("Только лидер команды может приглашать участников.");
        }

        if (leaderId.equals(targetUserId)) {
            throw new InviteException("Нельзя пригласить самого себя.");
        }

        if (!teamRepository.userExists(targetUserId)) {
            throw new UserNotFoundException("Пользователь с ID " + targetUserId + " не найден");
        }

        Integer targetTeamId = teamRepository.findCurrentTeamIdByUserId(targetUserId).orElse(null);
        if (targetTeamId != null) {
            throw new TeamNotFoundException("Пользователь уже состоит в другой команде и не может быть приглашён");
        }

        teamRepository.addActiveMember(teamId, targetUserId);
        String teamName = teamRepository.findTeamNameById(teamId).orElse("команды");
        log.info("Лидер ID={} пригласил пользователя ID={} в команду '{}' (ID:{})",
                leaderId, targetUserId, teamName, teamId);
    }


}