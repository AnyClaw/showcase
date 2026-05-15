package com.example.showcase.exception;

public class TeamNotFoundException extends RuntimeException {

    public TeamNotFoundException(int userId) {
        super("Команда не найдена для пользователя с id=" + userId);
    }

    public TeamNotFoundException(String message) {
        super(message);
    }

}
