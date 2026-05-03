package com.example.showcase.mapper;

import com.example.showcase.dto.response.ProjectDTO;
import com.example.showcase.entity.Project;

public class ProjectMapper {

    private ProjectMapper(){}

    public static ProjectDTO entityToDto(Project entity) {
        return new ProjectDTO(
                entity.getId(),
                entity.getTitle(),
                entity.getTarget(),
                entity.getBarrier(),
                entity.getExistingSolution(),
                entity.getDepartment(),
                entity.getStatus().getName(),
                UserMapper.entityToDto(entity.getOwner())
        );
    }
}
