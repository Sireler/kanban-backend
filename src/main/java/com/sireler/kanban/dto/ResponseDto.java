package com.sireler.kanban.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
public class ResponseDto {

    @JsonIgnore
    private HttpStatus httpStatus;

    @JsonProperty("success")
    private Boolean success;

    @JsonProperty("message")
    private String message;

    public ResponseDto(HttpStatus httpStatus, Boolean success, String message) {
        this.httpStatus = httpStatus;
        this.success = success;
        this.message = message;
    }
}
