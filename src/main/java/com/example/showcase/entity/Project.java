package com.example.showcase.entity;

import com.example.showcase.enums.ProjectStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "projects")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Integer id;

    private String title;
    private String target;
    private String barrier;
    private String existingSolution;
    private String projectType;
    private String department;

    @Enumerated(EnumType.STRING)
    @Column(name = "project_status")
    private ProjectStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;
}
