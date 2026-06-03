package com.example.showcase.dto.response;

public record DictionaryItemDTO(
        String value, // Техническое значение (например, "APPLIED"), уйдет обратно на бэк
        String label  // Человекочитаемое название (например, "Прикладной"), покажется в списке
) {}
