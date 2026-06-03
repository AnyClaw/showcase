package com.example.showcase.controller;

import com.example.showcase.dto.response.TeamDTO;
import com.example.showcase.entity.User;
import com.example.showcase.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/group")
@RequiredArgsConstructor

public class GroupController {

    private final GroupService groupService;


    @GetMapping("/my")
    @PreAuthorize("hasAuthority('STUDENT')")
    public void getMyTeam(
            @AuthenticationPrincipal User currentUser) {
        int userId = currentUser.getId();
//        return GroupService.getMyTeam(userId);
    }
}
