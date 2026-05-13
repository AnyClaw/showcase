package com.example.showcase.mapper;

import com.example.showcase.dto.request.ProjectRequestDTO;
import com.example.showcase.dto.response.ProjectResponseDTO;
import com.example.showcase.entity.Project;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ProjectMapper {

    ProjectResponseDTO toDto(Project project);

    Project toEntity(ProjectRequestDTO projectRequestDTO);
}
