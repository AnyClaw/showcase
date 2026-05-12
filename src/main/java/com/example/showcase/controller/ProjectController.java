package com.example.showcase.controller;

import com.example.showcase.dto.request.ProjectRequestDTO;
import com.example.showcase.dto.response.ProjectResponseDTO;
import com.example.showcase.enums.ProjectStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
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

    @GetMapping()
    Iterable<ProjectResponseDTO> findProjects(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "2") Integer size,
            @RequestParam(required = false) ProjectStatus projectStatus,
            @RequestParam(required = false) String projectType,
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Integer teamId
    );
}
