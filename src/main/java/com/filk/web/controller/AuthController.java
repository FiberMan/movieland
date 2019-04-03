package com.filk.web.controller;

import com.filk.dto.LoginResponseDto;
import com.filk.entity.Session;
import com.filk.service.SecurityService;
import com.filk.util.RequestCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
    private SecurityService securityService;

    @Autowired
    public AuthController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody RequestCredentials requestCredentials) {
        Session session = securityService.login(requestCredentials);
        return new LoginResponseDto(session.getToken(), session.getUser().getName());
    }

    @PostMapping("/logout")
    public void logout(@RequestHeader("uuid") String uuid) {
        securityService.logout(uuid);
    }
}
