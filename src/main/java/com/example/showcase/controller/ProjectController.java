package com.example.showcase.controller;

import com.example.showcase.dto.request.ProjectRequestDTO;
import com.example.showcase.dto.response.ProjectResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/project")
public interface ProjectController {

    @GetMapping("/all")
    @Operation(
            summary = "Получение всех проектов",
            description = """
                    Получает всю информацию о всех проектах
                    в базе данных
                """
    )
    @ApiResponse(responseCode = "200", description = "Все данные получены")
    Iterable<ProjectResponseDTO> findAllProjects();

    @GetMapping("/{id}")
    @Operation(
            summary = "Получение проекта по id",
            description = """
                    Получает всю информацию проекте по его id
                """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Все данные получены"),
            @ApiResponse(responseCode = "404", description = "Не найден проект по данному id")
    })
    ProjectResponseDTO findProjectById(
            @PathVariable("id") int id
    );

    // тестовый эндпоинт, потом удалить
    @PreAuthorize("hasAuthority('CLIENT')")
    @PostMapping("/add")
    ProjectResponseDTO addProject(@RequestBody ProjectRequestDTO projectDTO);
}
