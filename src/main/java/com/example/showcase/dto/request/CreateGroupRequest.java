package com.example.showcase.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateGroupRequest(
        @NotBlank(message = "Название группы обязательно")
        String groupName,

        @NotNull(message = "Необходимо указать ID преподавателя-куратора")
        Integer teacherId
) {}
