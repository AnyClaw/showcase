package com.example.showcase.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Integer group_id;

    @Column(name = "group_name", nullable = false)
    private String group_name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User group_owner;

    // Пользователи, принадлежащие группе
    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
    private List<User> members = new ArrayList<>();

}
