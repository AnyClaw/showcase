package com.example.showcase.controller;

import com.example.showcase.dto.response.ProjectBriefDTO;
import com.example.showcase.dto.response.UserResponseDTO;
import com.example.showcase.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
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
    Iterable<UserResponseDTO> findAllUsers();

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
    UserResponseDTO findUserById(
            @PathVariable("id") int id
    );

    @GetMapping("/search")
    @PreAuthorize("isAuthenticated()")
    ResponseEntity<UserResponseDTO> findUserByEmail(@RequestParam("email") String email);

    @GetMapping("/me/projects")
    @PreAuthorize("hasAuthority('STUDENT')")
    @Operation(
            summary = "История проектов текущего пользователя",
            description = """
                Возвращает все проекты, в которых участвовал текущий пользователь,
                с возможностью фильтрации по кафедре, типу проекта и поиску по названию.
                """
    )
    @ApiResponse(responseCode = "200", description = "Проекты успешно получены")
    List<ProjectBriefDTO> getMyProjectHistory(
            @AuthenticationPrincipal User currentUser,
            @RequestParam(required = false) String department,
            @RequestParam(name = "project-type", required = false) String projectType,
            @RequestParam(required = false) String title
    );

}
