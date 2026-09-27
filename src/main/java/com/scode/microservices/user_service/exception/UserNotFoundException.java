package com.scode.microservices.user_service.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String errorMsg) {
        super(errorMsg);
    }
}
