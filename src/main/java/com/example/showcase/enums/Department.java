package com.example.showcase.enums;

public enum Department {
    CTUTP("ЦТУТП"),
    IVT("Кафедра ИВТ"),
    ECONOMICS("Кафедра Экономики");

    private final String label;

    Department(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}