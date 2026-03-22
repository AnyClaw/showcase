package com.example.showcase.entity;

import com.example.showcase.enums.ProjectStatusName;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "statuses")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProjectStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_name", unique = true, nullable = false)
    private ProjectStatusName name;
}
