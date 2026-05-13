package com.example.showcase.exception;

import com.example.showcase.dto.response.ErrorResponse;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Arrays;

@Hidden
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // TODO: Добавить остальные исключения

    @ExceptionHandler({UserNotFoundException.class, ProjectNotFoundException.class})
    public ResponseEntity<String> handle(RuntimeException e) {
        return ResponseEntity.status(404).body(e.toString());
    }

    // Когда @Valid DTO не проходит валидацию
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException e, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse
                .of(HttpStatus.BAD_REQUEST, request)
                .message("Incorrect params in request body: " + Arrays.toString(e.getDetailMessageArguments()))
                .code("VALIDATION_ERROR")
                .build()
        );
    }

    // когда тело запроса имеет некорректный JSON
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handle(HttpMessageNotReadableException e, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse
                .of(HttpStatus.BAD_REQUEST, request)
                .message("Incorrect request body")
                .code("MALFORMED_JSON")
                .build()
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handle(BadCredentialsException e, WebRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorResponse
                .of(HttpStatus.UNAUTHORIZED, request)
                .message("Incorrect login or password")
                .code("INVALID_CREDENTIALS")
                .build()
        );
    }

    @ExceptionHandler(TokenRefreshException.class)
    public ResponseEntity<ErrorResponse> handle(TokenRefreshException e, WebRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorResponse
                .of(HttpStatus.UNAUTHORIZED, request)
                .message(e.getMessage())
                .code("TOKEN_EXPIRED")
                .build()
        );
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ErrorResponse> handle(AuthorizationDeniedException e, WebRequest request) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ErrorResponse
                .of(HttpStatus.FORBIDDEN, request)
                .message("Not enough rights")
                .code("ACCESS_DENIED")
                .build()
        );
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<ErrorResponse> handle(AuthenticationCredentialsNotFoundException e, WebRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorResponse
                .of(HttpStatus.UNAUTHORIZED, request)
                .message("Token is missing or invalid")
                .code("UNAUTHENTICATED")
                .build()
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handle(MethodArgumentTypeMismatchException e, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse
                .of(HttpStatus.BAD_REQUEST, request)
                .message(String.format("Parameter '%s' has an incorrect value '%s'", e.getName(), e.getValue()))
                .code("INVALID_PARAMETER_VALUE")
                .build()
        );
    }
}
