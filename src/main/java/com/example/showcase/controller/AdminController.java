package com.example.showcase.controller;

import com.example.showcase.dto.request.AssignStudentsToGroupRequest;
import com.example.showcase.dto.request.CreateGroupRequest;
import com.example.showcase.dto.response.GroupCreatedDTO;
import com.example.showcase.dto.response.GroupWithStudentsDTO;
import com.example.showcase.dto.response.ProjectBriefDTO;
import com.example.showcase.dto.response.UserBriefDTO;
import com.example.showcase.enums.ProjectStatus;
import com.example.showcase.service.GroupService;
import com.example.showcase.service.ProjectService;
import com.example.showcase.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final GroupService groupService;
    private final ProjectService projectService;
    private final UserService userService;

    @PostMapping("/group/create")
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Operation(summary = "Создать новую группу")
    public GroupCreatedDTO createGroup(@RequestBody @Valid CreateGroupRequest request) {
        return groupService.createGroup(request);
    }

    @PutMapping("/{groupId}/add")
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    public void assignStudentsToGroup(
            @PathVariable Integer groupId,
            @RequestBody @Valid AssignStudentsToGroupRequest request) {

        groupService.assignStudentsToGroup(groupId, request);
    }

    @DeleteMapping("/{groupId}/remove")
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Operation(summary = "Удаление студентов из группы")
    public void removeStudentsFromGroup(
            @PathVariable Integer groupId,
            @RequestBody @Valid AssignStudentsToGroupRequest request) {

        groupService.removeStudentsFromGroup(groupId, request);
    }
    @GetMapping("/groups")
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Operation(summary = "Получить все группы с их студентами")
    public List<GroupWithStudentsDTO> getAllGroupsWithStudents(
            @RequestParam(required = false) String groupName) {

        return groupService.getAllGroupsWithStudents(groupName);
    }

    @GetMapping("/projects")
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Operation(summary = "Получить все проекты с фильтрацией")
    public List<ProjectBriefDTO> getAllProjectsForAdmin(
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String projectType,
            @RequestParam(required = false) ProjectStatus status,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Integer groupId,
            @RequestParam(required = false) Integer teamId) {

        return projectService.getAllProjectsForAdmin(
                department,
                projectType,
                status,
                title,
                groupId,
                teamId
        );
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Operation(summary = "Получить всех пользователей с фильтрацией")
    public List<UserBriefDTO> getAllUsersForAdmin(
            @RequestParam(required = false) String role,
            @RequestParam(required = false) Integer groupId,
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) String email) {

        return userService.getAllUsersForAdmin(role, groupId, fullName, email);
    }
}
