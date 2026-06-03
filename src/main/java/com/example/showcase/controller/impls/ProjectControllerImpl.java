package com.example.showcase.controller.impls;

import com.example.showcase.controller.ProjectController;
import com.example.showcase.dto.request.ProjectCreateRequestDTO;
import com.example.showcase.dto.request.ProjectRequestDTO;
import com.example.showcase.dto.response.PageResponse;
import com.example.showcase.dto.response.ProjectBriefDTO;
import com.example.showcase.dto.response.ProjectResponseDTO;
import com.example.showcase.entity.User;
import com.example.showcase.enums.ProjectStatus;
import com.example.showcase.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    @Override
    public ProjectResponseDTO createProject(
             User currentUser,
            ProjectCreateRequestDTO request) {

        return projectService.createProject(request, currentUser.getId());
    }
    @Override
    public List<ProjectBriefDTO> getMyProjects(
            User currentUser,
            String department,
            String projectType,
            ProjectStatus status,
            String title
    ) {
        return projectService.getMyProjectsAsClient(
                currentUser.getId(),
                department,
                projectType,
                status,
                title
        );
    }
}