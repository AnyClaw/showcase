package com.example.showcase.dto.request;

import com.example.showcase.enums.Department;
import com.example.showcase.enums.ProjectType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProjectCreateRequestDTO(
        @NotBlank(message = "Название проекта обязательно")
        String title,
        @NotNull(message = "Тип проекта обязателен")
        ProjectType projectType,
        @NotNull(message = "Кафедра обязательна")
        Department department,
        @NotBlank(message = "Цель проекта обязательна")
        String target,
        @NotBlank(message = "Барьер обязателен")
        String barrier,
        @NotBlank(message = "Существующее решение обязательно")
        String existingSolution
) {}
