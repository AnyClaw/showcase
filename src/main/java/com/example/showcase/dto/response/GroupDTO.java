package com.example.showcase.dto.response;

public record  GroupDTO (
    Integer group_id,
    String gropu_name,
    Integer group_owner,
    String ownerEmail
){ }
