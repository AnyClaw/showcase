package com.example.showcase.controller.impls;

import com.example.showcase.controller.UserController;
import com.example.showcase.dto.response.ProjectBriefDTO;
import com.example.showcase.dto.response.UserResponseDTO;
import com.example.showcase.entity.User;
import com.example.showcase.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    public Iterable<UserResponseDTO> findAllUsers() {
        return userService.getAllUsers();
    }

    @Override
    public UserResponseDTO findUserById(int id) {
        return userService.getById(id);
    }

    @Override
    public ResponseEntity<UserResponseDTO> findUserByEmail(@RequestParam("email") String email) {
        UserResponseDTO result = userService.findUserByEmail(email);
        return ResponseEntity.ok(result);
    }

    @Override
    public List<ProjectBriefDTO> getMyProjectHistory(
            @AuthenticationPrincipal User currentUser,
            @RequestParam(required = false) String department,
            @RequestParam(name = "project-type", required = false) String projectType,
            @RequestParam(required = false) String title) {

        return userService.getUserProjectHistory(
                currentUser.getId(),
                department,
                projectType,
                title
        );
    }
}
