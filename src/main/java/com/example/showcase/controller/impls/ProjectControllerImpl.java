package com.example.showcase.controller.impls;

import com.example.showcase.controller.ProjectController;
import com.example.showcase.dto.ProjectDTO;
import com.example.showcase.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProjectControllerImpl implements ProjectController {

    private final ProjectService projectService;

    @Override
    public Iterable<ProjectDTO> findAllProjects() {
        return projectService.getAllProjects();
    }

    @Override
    public ProjectDTO findProjectById(int id) {
        return projectService.getById(id);
    }
}
