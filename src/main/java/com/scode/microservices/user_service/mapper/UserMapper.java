package com.scode.microservices.user_service.mapper;


import com.scode.microservices.user_service.dto.CreateUserDto;
import com.scode.microservices.user_service.dto.UserDto;
import com.scode.microservices.user_service.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public static UserDto toDto(User user){
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }

    public static User toEntity(CreateUserDto userDto){
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        return user;
    }
}
