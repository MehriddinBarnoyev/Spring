package com.mohirdev.mohirdev.exceptions;


import org.springframework.security.core.AuthenticationException;

public class UserNotActiveException extends AuthenticationException {
    public UserNotActiveException(String explanation) {

        super(explanation);
    }

    public UserNotActiveException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
