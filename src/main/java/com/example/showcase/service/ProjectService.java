package com.example.showcase.service;

import com.example.showcase.dto.ProjectDTO;
import com.example.showcase.entity.Project;
import com.example.showcase.exception.ProjectNotFoundException;
import com.example.showcase.mapper.ProjectMapper;
import com.example.showcase.repository.ProjectsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectsRepository projectsRepository;

    public List<ProjectDTO> getAllProjects() {
        Iterable<Project> projects = projectsRepository.findAll();

        return StreamSupport.stream(projects.spliterator(), false)
                .map(ProjectMapper::entityToDto)
                .toList();
    }

    public ProjectDTO getById(int id) {
        return ProjectMapper.entityToDto(projectsRepository
                .findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(id))
        );
    }
}
