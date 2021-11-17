package com.sireler.kanban.exception;

import com.sireler.kanban.security.jwt.JwtAuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestControllerExceptionHandler {

    @ExceptionHandler(KanbanApiException.class)
    public ResponseEntity<?> resolveException(KanbanApiException exception) {
        Map<String, String> error = new HashMap<>();
        error.put("error", exception.getMessage());

        return new ResponseEntity<>(error, exception.getHttpStatus());
    }

    @ExceptionHandler(JwtAuthenticationException.class)
    public ResponseEntity<?> resolveException(JwtAuthenticationException exception) {
        Map<String, String> error = new HashMap<>();
        error.put("error", exception.getMessage());

        KanbanApiException apiException = new KanbanApiException(HttpStatus.UNAUTHORIZED, exception.getMessage());

        return new ResponseEntity<>(error, apiException.getHttpStatus());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> resolveException(RuntimeException e) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "An internal server error occurred.");

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
