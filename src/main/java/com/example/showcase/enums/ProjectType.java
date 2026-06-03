package com.example.showcase.enums;

public enum ProjectType {
    APPLIED("Прикладной"),
    EDUCATIONAL_APPLIED("Учебно-прикладной");

    private final String label;

    ProjectType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
