package com.scode.microservices.user_service.service;

import com.scode.microservices.user_service.dto.*;

public interface AuthService {
    RegisterUserResponseDto registerUser(RegisterDto registerDto);

    LoginResponseDto login(LoginDto loginDto);
}
