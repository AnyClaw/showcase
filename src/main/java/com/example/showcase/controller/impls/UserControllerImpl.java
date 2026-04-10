package com.example.showcase.controller.impls;

import com.example.showcase.controller.UserController;
import com.example.showcase.dto.response.UserDTO;
import com.example.showcase.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    public Iterable<UserDTO> findAllUsers() {
        return userService.getAllUsers();
    }

    @Override
    public UserDTO findUserById(int id) {
        return userService.getById(id);
    }
}
