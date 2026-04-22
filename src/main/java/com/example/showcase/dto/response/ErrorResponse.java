package com.example.showcase.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String code,
        String path
) {
    public static ErrorResponseBuilder of(HttpStatus status, WebRequest request) {
        return ErrorResponse.builder()
                .status(status.value())
                .timestamp(LocalDateTime.now())
                .error(status.getReasonPhrase())
                .path(request.getDescription(false).replace("uri=", ""));
    }
}
