package com.sireler.kanban.exception;

import org.springframework.http.HttpStatus;

public class KanbanApiException extends RuntimeException {

    private final HttpStatus httpStatus;

    private final String message;

    public KanbanApiException(HttpStatus httpStatus, String message) {
        super();
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public KanbanApiException(HttpStatus httpStatus, String message, Throwable cause) {
        super(message, cause);
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}
