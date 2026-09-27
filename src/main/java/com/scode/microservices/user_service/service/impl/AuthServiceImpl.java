package com.scode.microservices.user_service.service.impl;

import com.scode.microservices.user_service.dto.*;
import com.scode.microservices.user_service.entity.Role;
import com.scode.microservices.user_service.entity.User;
import com.scode.microservices.user_service.repositories.UserRepo;
import com.scode.microservices.user_service.security.JwtService;
import com.scode.microservices.user_service.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public RegisterUserResponseDto registerUser(RegisterDto registerDto) {
        User user = new User();
        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRole(registerDto.getRole());

        User savedUser = userRepo.save(user);
        log.info("New User registered successfully: id={}, name={}, role={}",
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getRole());
        return new RegisterUserResponseDto(savedUser.getId(), savedUser.getName());
    }

    public LoginResponseDto login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        String jwtToken = jwtService.generateJwtToken((UserDetails) Objects.requireNonNull(authentication.getPrincipal()));
        log.info("Login successful for user: {}", loginDto.getEmail());
        return new LoginResponseDto(jwtToken);
    }
}
