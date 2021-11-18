package com.sireler.kanban.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class JwtDto {

    private String username;

    private String token;
}
