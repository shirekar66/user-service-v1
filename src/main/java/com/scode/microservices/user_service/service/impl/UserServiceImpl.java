package com.scode.microservices.user_service.service.impl;

import com.scode.microservices.user_service.dto.PatchUserDto;
import com.scode.microservices.user_service.dto.UpdateUserDto;
import com.scode.microservices.user_service.dto.UserDto;
import com.scode.microservices.user_service.entity.User;
import com.scode.microservices.user_service.exception.UserNotFoundException;
import com.scode.microservices.user_service.mapper.UserMapper;
import com.scode.microservices.user_service.repositories.UserRepo;
import com.scode.microservices.user_service.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Cacheable(value = "users", key = "'allUsers'")
    public List<UserDto> getAllUsers() {

        List<UserDto> users = userRepo.findAll()
                .stream()
                .map(UserMapper::toDto)
                .toList();
        log.info("Successfully fetched {} users", users.size());
        return users;

    }

    @Override
    @Cacheable(value = "users", key = "#id")
    public UserDto getUserById(Long id) {
        User user = getUserEntity(id);
        log.info("Successfully fetched user with id {}", id);
        return UserMapper.toDto(user);
    }

    @Override
    @Caching(
            put = @CachePut(value = "users", key = "#id"),
            evict = @CacheEvict(value = "users", key = "'allUsers'")
    )
    public UserDto updateUser(Long id, UpdateUserDto updateUserDto) {
        User existingUser = getUserEntity(id);
        existingUser.setName(updateUserDto.getName());
        existingUser.setEmail(updateUserDto.getEmail());
        existingUser.setPassword(passwordEncoder.encode(updateUserDto.getPassword()));
        log.info("Successfully updated user with id {}", id);
        return UserMapper.toDto(existingUser);
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(value = "users", key = "#id"),
                    @CacheEvict(value = "users", key = "'allUsers'")
            }
    )
    public void deleteUser(Long id) {
        User existingUser = getUserEntity(id);
        userRepo.deleteById(existingUser.getId());
        log.info("User deleted successfully with id: {}", id);

    }

    @Caching(
            put = @CachePut(value = "users", key = "#id"),
            evict = @CacheEvict(value = "users", key = "'allUsers'")
    )
    public UserDto patchUser(Long id, PatchUserDto patchUserDto) {
        User user = getUserEntity(id);
        user.setName(patchUserDto.getName());
        user.setEmail(patchUserDto.getEmail());
        user.setPassword(passwordEncoder.encode(patchUserDto.getPassword()));
        log.info("Successfully patched user with id {}", id);
        return UserMapper.toDto(user);
    }

    private User getUserEntity(Long id) {
        return userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("User Not found with id: " + id));
    }
}
