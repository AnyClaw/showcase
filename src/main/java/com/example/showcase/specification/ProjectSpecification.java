package com.example.showcase.specification;

import com.example.showcase.entity.Project;
import com.example.showcase.enums.ProjectStatus;
import org.springframework.data.jpa.domain.Specification;

public class ProjectSpecification {

    private ProjectSpecification(){}

    // TODO: teamId

    public static Specification<Project> hasProjectStatus(ProjectStatus projectStatus) {
        return (root, query, cb) ->
                projectStatus == null
                        ? cb.conjunction()
                        : cb.equal(root.get("status"), projectStatus);
    }

    public static Specification<Project> hasProjectType(String projectType) {
        return (root, query, cb) ->
                projectType == null || projectType.isBlank()
                        ? cb.conjunction()
                        : cb.equal(cb.lower(root.get("projectType")), projectType.toLowerCase());
    }

    public static Specification<Project> hasDepartment(String department) {
        return (root, query, cb) ->
                department == null || department.isBlank()
                        ? cb.conjunction()
                        : cb.equal(cb.lower(root.get("department")), department.toLowerCase());
    }

    public static Specification<Project> hasTitle(String title) {
        return (root, query, cb) ->
                title == null || title.isBlank()
                        ? cb.conjunction()
                        : cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    public static Specification<Project> excludeVerification() {
        return (root, query, cb) ->
                cb.notEqual(root.get("status"), ProjectStatus.ON_VERIFICATION);
    }

    public static Specification<Project> buildFilter(
            ProjectStatus projectStatus, String projectType, String department, String title
    ) {
        return Specification
                .where(excludeVerification())
                .and(hasProjectStatus(projectStatus))
                .and(hasProjectType(projectType))
                .and(hasDepartment(department))
                .and(hasTitle(title));
    }
}
