package com.example.showcase.controller;

import com.example.showcase.dto.UserDTO;
import com.example.showcase.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    public Iterable<UserDTO> findAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserDTO findUserById(@PathVariable("id") int id) {
        return userService.getById(id);
    }
}
