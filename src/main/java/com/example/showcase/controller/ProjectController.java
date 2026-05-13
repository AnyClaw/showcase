package com.example.showcase.controller;

import com.example.showcase.dto.request.ProjectRequestDTO;
import com.example.showcase.dto.response.PageResponse;
import com.example.showcase.dto.response.ProjectResponseDTO;
import com.example.showcase.enums.ProjectStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
public interface ProjectController {

    @GetMapping()
    @Operation(
            summary = "Получение проектов с фильтрами",
            description = """
                    Получение всех проектов с пагинацией и применением фильтров в параметрах запроса.
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Проекты успешно получены",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject("""
                                    {
                                        "content": [...],
                                        "page": 0,
                                        "size": 2,
                                        "totalElements": 1,
                                        "totalPages": 1
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Запрос сформирован некорректно (параметр указан некорректно)",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject("""
                                    {
                                        "timestamp": "2026-05-12T22:13:35.7913205",
                                        "status": 400,
                                        "error": "Bad Request",
                                        "message": "Parameter 'project-status' has an incorrect value 'IN_PROGRES'",
                                        "code": "INVALID_PARAMETER_VALUE",
                                        "path": "/api/projects"
                                    }
                                    """)
                    )
            )
    })
    PageResponse<ProjectResponseDTO> findProjects(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "2") Integer size,
            @RequestParam(name = "project-status", required = false) ProjectStatus status,
            @RequestParam(name = "project-type", required = false) String type,
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Integer teamId
    );

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

    // TODO: добавить запись в project_stage при создании проекта; SWAGGER документация
    @PreAuthorize("hasAuthority('CLIENT')")
    @PostMapping("/add")
    ProjectResponseDTO addProject(@RequestBody ProjectRequestDTO projectDTO);
}
