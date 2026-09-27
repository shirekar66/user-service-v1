package com.scode.microservices.user_service.dto;

import com.scode.microservices.user_service.entity.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {

    @NotBlank
    @NonNull
    private String name;
    @NotBlank
    @NonNull
    private String email;
    @NotBlank
    @NonNull
    private String password;
    @NotBlank
    @NonNull
    private Role role;
}