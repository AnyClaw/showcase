package com.example.showcase.controller;

import com.example.showcase.dto.response.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
//@PreAuthorize("hasAuthority('ADMIN')")
public interface UserController {

    @GetMapping("/all")
    @Operation(
            summary = "Получение всех пользователей",
            description = """
                    Получает всю информацию (кроме паролей)
                    о всех пользователях в базе данных
                """
    )
    @ApiResponse(responseCode = "200", description = "Все данные получены")
    Iterable<UserDTO> findAllUsers();

    @GetMapping("/{id}")
    @Operation(
            summary = "Получение проекта по id",
            description = """
                    Получает всю информацию (кроме пароля) о пользователе
                    по его id
                """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Все данные получены"),
            @ApiResponse(responseCode = "404", description = "Не найден пользователь по данному id")
    })
    UserDTO findUserById(
            @PathVariable("id") int id
    );
}
