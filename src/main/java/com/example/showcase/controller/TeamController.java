package com.example.showcase.controller;

import com.example.showcase.dto.request.CreateTeamRequest;
import com.example.showcase.dto.response.ProjectBriefDTO;
import com.example.showcase.dto.response.TeamDTO;
import com.example.showcase.entity.User;
import com.example.showcase.exception.UserNotFoundException;
import com.example.showcase.service.TeamService;
import com.example.showcase.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.List;


@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;
    private final UserService userService;

    @GetMapping("/members")
    @PreAuthorize("hasAuthority('STUDENT')")
    public TeamDTO getMyTeam(
            @AuthenticationPrincipal User currentUser) {
        int userId = currentUser.getId();
        return teamService.getMyTeamMembers(userId);
    }

    @GetMapping("/projects")
    @PreAuthorize("hasAuthority('STUDENT')")
    public List<ProjectBriefDTO> getMyTeamProjects(
            @AuthenticationPrincipal User currentUser,
            @RequestParam(required = false) String department,
            @RequestParam(name = "project-type", required = false) String projectType,
            @RequestParam(name = "project-status", required = false) String status,
            @RequestParam(required = false) String title
    ) {
        return teamService.getMyTeamProjectsBrief(currentUser.getId(),
                department,
                projectType,
                status,
                title );
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
    //вопрос
    @PostMapping("/invite-by-email")
    @PreAuthorize("hasAuthority('STUDENT')")
    public void inviteUserToTeamByEmail(
            @AuthenticationPrincipal User currentUser,
            @RequestParam String email,
            WebRequest request) {

        if (currentUser == null || currentUser.getId() == null) {
            throw new UserNotFoundException("User not authenticated");
        }

        teamService.inviteUserToTeamByEmail(currentUser.getId(), email);
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
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('STUDENT')")
    public TeamDTO createTeam(
            @AuthenticationPrincipal User currentUser,
            @RequestBody @Valid CreateTeamRequest request) {

        return teamService.createTeam(currentUser.getId(), request.name());
    }

}

