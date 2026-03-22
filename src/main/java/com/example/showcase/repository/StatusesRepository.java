package com.example.showcase.repository;

import com.example.showcase.entity.ProjectStatus;
import com.example.showcase.enums.ProjectStatusName;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusesRepository extends CrudRepository<ProjectStatus, Integer> {
    Optional<ProjectStatus> findByName(ProjectStatusName name);
}
