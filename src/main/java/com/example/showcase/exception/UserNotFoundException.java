package com.example.showcase.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(int userId) {
        super("User with id = " + userId + " not found");
    }

    public UserNotFoundException(String email) {
        super("Пользователь с email " + email + " не найден");
    }


}
