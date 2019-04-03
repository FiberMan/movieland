package com.filk.exception;

public class UserBadPassword extends RuntimeException {
    public UserBadPassword(String message) {
        super(message);
    }
}
