package com.example.showcase.controller;

import com.example.showcase.dto.response.DictionaryItemDTO;
import com.example.showcase.enums.Department;
import com.example.showcase.enums.ProjectType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/dictionaries")
@Tag(name = "Справочники", description = "Эндпоинты для получения списков значений (выпадающие списки)")
public class DictionaryController {

    @GetMapping("/project-types")
    @Operation(summary = "Получить список типов проектов")
    public List<DictionaryItemDTO> getProjectTypes() {
        return Arrays.stream(ProjectType.values())
                .map(type -> new DictionaryItemDTO(type.name(), type.getLabel()))
                .toList();
    }

    @GetMapping("/departments")
    @Operation(summary = "Получить список кафедр")
    public List<DictionaryItemDTO> getDepartments() {
        return Arrays.stream(Department.values())
                .map(dept -> new DictionaryItemDTO(dept.name(), dept.getLabel()))
                .toList();
    }
}