package com.example.showcase.repository;

import com.example.showcase.entity.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectsRepository extends CrudRepository<Project, Integer> {

}
