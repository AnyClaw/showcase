package com.example.showcase.controller;

import com.example.showcase.dto.response.ErrorResponse;
import com.example.showcase.dto.response.TeamDTO;
import com.example.showcase.entity.User;
import com.example.showcase.service.TeamService;
import com.example.showcase.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;


@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;
    private final UserService userService;

    @GetMapping("/my")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<TeamDTO> getMyTeam(
            @AuthenticationPrincipal User currentUser) {

        int userId = currentUser.getId();

        TeamDTO team = teamService.getMyTeam(userId);

        return ResponseEntity.ok(team);
    }

    @PatchMapping("/leave")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<ErrorResponse> leaveTeam(
            @AuthenticationPrincipal User currentUser,
            @RequestParam(required = false)  Integer  newLeaderId,
            WebRequest request) {

        teamService.leaveTeam(currentUser.getId(), newLeaderId);

        return ResponseEntity.status(HttpStatus.OK).body(ErrorResponse
                .of(HttpStatus.OK, request)
                .message("Вы успешно покинули команду")
                .code("TEAM_LEFT")
                .build()
        );
    }

    @PatchMapping("/exclude/{userId}")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<ErrorResponse> excludeUser(
            @AuthenticationPrincipal User currentUser,
            @PathVariable int userId,
            WebRequest request) {

        teamService.excludeUser(currentUser.getId(), userId);

        return ResponseEntity.status(HttpStatus.OK).body(ErrorResponse
                .of(HttpStatus.OK, request)
                .message("Пользователь успешно исключен из команды")
                .code("USER_EXCLUDED")
                .build()
        );
    }

    @PatchMapping("/leader/{userId}")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<ErrorResponse> changeTeamLeader(
            @AuthenticationPrincipal User currentUser,
            @PathVariable int userId,
            WebRequest request) {

        teamService.changeTeamLeader(currentUser.getId(), userId);

        return ResponseEntity.status(HttpStatus.OK).body(ErrorResponse
                .of(HttpStatus.OK, request)
                .message("Лидер команды успешно изменён")
                .code("LEADER_CHANGED")
                .build()
        );
    }

    @PostMapping("/invite/{userId}")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<ErrorResponse> inviteUserToTeam(
            @AuthenticationPrincipal User currentUser,
            @PathVariable int userId,
            WebRequest request) {

        teamService.inviteUserToTeam(currentUser.getId(), userId);

        return ResponseEntity.status(HttpStatus.OK).body(ErrorResponse
                .of(HttpStatus.OK, request)
                .message("Пользователь успешно приглашён в команду")
                .code("USER_INVITED")
                .build()
        );
    }

}

