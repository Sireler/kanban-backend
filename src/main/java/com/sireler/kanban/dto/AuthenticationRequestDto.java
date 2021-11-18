package com.sireler.kanban.dto;

import lombok.Data;

@Data
public class AuthenticationRequestDto {

    private String username;

    private String password;
}
