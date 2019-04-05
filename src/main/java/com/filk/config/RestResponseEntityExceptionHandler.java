package com.filk.config;

import com.filk.exception.UserBadPassword;
import com.filk.exception.UserNotAuthorized;
import com.filk.exception.UserNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    public RestResponseEntityExceptionHandler() {
        super();
    }

    @ExceptionHandler(value = {UserNotFound.class, UserBadPassword.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Email or password is wrong.")
    protected void handleUserCredentials() {
    }

    @ExceptionHandler(value = {UserNotAuthorized.class})
    @ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "No access for current user to this resource")
    protected void handleUserAuthorization() {
    }
}