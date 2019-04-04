package com.filk.exception;

public class UserNotAuthorized extends RuntimeException {
    public UserNotAuthorized(String message) {
        super(message);
    }
}
