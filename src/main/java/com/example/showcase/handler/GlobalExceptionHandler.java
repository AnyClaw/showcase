package com.example.showcase.handler;

import com.example.showcase.exception.ProjectNotFoundException;
import com.example.showcase.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({UserNotFoundException.class, ProjectNotFoundException.class})
    public ResponseEntity<String> handle(RuntimeException e) {
        return ResponseEntity.status(404).body(e.toString());
    }
}
