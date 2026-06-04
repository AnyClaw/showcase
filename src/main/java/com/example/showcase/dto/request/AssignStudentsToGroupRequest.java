package com.example.showcase.dto.request;

import java.util.List;

public record AssignStudentsToGroupRequest(
        List<Integer> userIds
) {}
