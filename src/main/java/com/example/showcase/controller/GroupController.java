package com.example.showcase.controller;

import com.example.showcase.dto.response.GroupBriefDTO;
import com.example.showcase.dto.response.GroupWithStudentsDTO;
import com.example.showcase.dto.response.TeamDTO;
import com.example.showcase.entity.User;
import com.example.showcase.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @GetMapping("/my")
    @PreAuthorize("hasAnyAuthority('TEACHER', 'ADMINISTRATOR')")

    public List<GroupBriefDTO> getMyGroups(@AuthenticationPrincipal User currentUser) {
        return groupService.getMyGroups(currentUser.getId());
    }

    @GetMapping("/my/with-students")
    @PreAuthorize("hasAnyAuthority('TEACHER', 'ADMINISTRATOR')")
    public List<GroupWithStudentsDTO> getMyGroupsWithStudents(
            @AuthenticationPrincipal User currentUser,
            @RequestParam(required = false) String groupName) {
        return groupService.getGroupsWithStudents(currentUser.getId(), groupName);
    }
}
