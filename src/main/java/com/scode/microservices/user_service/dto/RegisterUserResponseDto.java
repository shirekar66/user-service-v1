package com.scode.microservices.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterUserResponseDto {

    private Long id;
    private String name;
}
