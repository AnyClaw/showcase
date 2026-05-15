package com.example.showcase.controller.impls;

import com.example.showcase.controller.UserController;
import com.example.showcase.dto.response.UserResponseDTO;
import com.example.showcase.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
