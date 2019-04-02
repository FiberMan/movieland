package com.filk.web.controller;

import com.filk.dto.LoginResponseDto;
import com.filk.entity.Session;
import com.filk.exception.UserBadPassword;
import com.filk.exception.UserNotFound;
import com.filk.service.SecurityService;
import com.filk.util.RequestCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class AuthController {
    private SecurityService securityService;

    @Autowired
    public AuthController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody RequestCredentials requestCredentials) {
        try {
            Session session = securityService.login(requestCredentials);
            return new LoginResponseDto(session.getToken(), session.getUser().getName());
        } catch (UserNotFound | UserBadPassword e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email or password is wrong.");
        }
    }

    @PostMapping("/logout")
    public void logout(@RequestHeader("uuid") String uuid) {
        securityService.logout(uuid);
    }
}
