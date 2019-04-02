package com.filk.exception;

public class UserNotFound extends RuntimeException {
    public UserNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
