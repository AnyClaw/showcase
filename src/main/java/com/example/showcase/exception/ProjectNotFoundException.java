package com.example.showcase.exception;

public class ProjectNotFoundException extends RuntimeException {
    private final int projectId;

    public ProjectNotFoundException(int projectId) {
        super();

        this.projectId = projectId;
    }

    @Override
    public String toString() {
        return "Project with id = " + projectId + " not found";
    }
}
