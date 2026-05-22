package com.example.showcase.controller;

import com.example.showcase.dto.response.ErrorResponse;
import com.example.showcase.dto.response.TeamDTO;
import com.example.showcase.entity.User;
import com.example.showcase.exception.UserNotFoundException;
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
    public TeamDTO getMyTeam(
            @AuthenticationPrincipal User currentUser) {
        int userId = currentUser.getId();
        return teamService.getMyTeam(userId);
    }

    @PatchMapping("/leave")
    @PreAuthorize("hasAuthority('STUDENT')")
    public void leaveTeam(
            @AuthenticationPrincipal User currentUser,
            @RequestParam(required = false) Integer newLeaderId) {
        if (currentUser == null || currentUser.getId() == null)
            throw new UserNotFoundException("User not authenticated");
        teamService.leaveTeam(currentUser.getId(), newLeaderId);
    }


    @PatchMapping("/exclude/{userId}")
    @PreAuthorize("hasAuthority('STUDENT')")
    public void excludeUser(
            @AuthenticationPrincipal User currentUser,
            @PathVariable int userId) {

        if (currentUser == null || currentUser.getId() == null)
            throw new UserNotFoundException("User not authenticated");

        teamService.excludeUser(currentUser.getId(),userId);
    }

    @PatchMapping("/leader/{userId}")
    @PreAuthorize("hasAuthority('STUDENT')")
    public void changeTeamLeader(
            @AuthenticationPrincipal User currentUser,
            @PathVariable int userId,
            WebRequest request) {
        if (currentUser == null || currentUser.getId() == null)
            throw new UserNotFoundException("User not authenticated");
        teamService.changeTeamLeader(currentUser.getId(), userId);
    }

    @PostMapping("/invite/{userId}")
    @PreAuthorize("hasAuthority('STUDENT')")
    public void inviteUserToTeam(
            @AuthenticationPrincipal User currentUser,
            @PathVariable int userId,
            WebRequest request) {
        if (currentUser == null || currentUser.getId() == null)
            throw new UserNotFoundException("User not authenticated");
        teamService.inviteUserToTeam(currentUser.getId(), userId);
    }

}

