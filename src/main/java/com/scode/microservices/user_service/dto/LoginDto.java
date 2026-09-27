package com.scode.microservices.user_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data@AllArgsConstructor
public class LoginDto {

    @NotBlank
    @NonNull
    @Email(message = "Invalid email format")
    private String email;
    @NotBlank
    @NonNull
    private String password;
}
