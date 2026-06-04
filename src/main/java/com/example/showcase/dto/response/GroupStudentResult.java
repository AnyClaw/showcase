package com.example.showcase.dto.response;

public record GroupStudentResult (
    Integer groupId,
    String groupName,
    Integer studentId,
    String firstName,
    String lastName,
    String middleName,
    String email
){}
