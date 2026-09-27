package com.scode.microservices.user_service.service;

import com.scode.microservices.user_service.dto.PatchUserDto;
import com.scode.microservices.user_service.dto.UpdateUserDto;
import com.scode.microservices.user_service.dto.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> getAllUsers();

    UserDto getUserById(Long id);

    UserDto updateUser(Long id, UpdateUserDto updateUserDto);

    UserDto patchUser(Long id, PatchUserDto patchUserDto);

    void deleteUser(Long id);

}
