package com.example.showcase.exception;

public class UserNotFoundException extends RuntimeException {
    private final int userId;

    public UserNotFoundException(int userId) {
        super();
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "User with id = " + userId + " not found";
    }
}
