package com.example.showcase.controller.impls;

import com.example.showcase.controller.ProjectController;
import com.example.showcase.dto.request.ProjectRequestDTO;
import com.example.showcase.dto.response.PageResponse;
import com.example.showcase.dto.response.ProjectResponseDTO;
import com.example.showcase.enums.ProjectStatus;
import com.example.showcase.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProjectControllerImpl implements ProjectController {

    private final ProjectService projectService;

    @Override
    public ProjectResponseDTO findProjectById(int id) {
        return projectService.getById(id);
    }

    @Override
    public ProjectResponseDTO addProject(ProjectRequestDTO projectDTO) {
        return projectService.addProject(projectDTO);
    }

    @Override
    public PageResponse<ProjectResponseDTO> findProjects(
            Integer page, Integer size, ProjectStatus status,
            String type, String department, String title, Integer teamId
    ) {
        return projectService.findProjects(page, size, status, type, department, title, teamId);
    }
}