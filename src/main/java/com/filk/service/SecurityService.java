package com.filk.service;

import com.filk.entity.Session;
import com.filk.util.RequestCredentials;

import java.util.Optional;

public interface SecurityService {
    Session login(RequestCredentials requestCredentials);
    void logout(String token);
    Optional<Session> getValidSession(String token);
}
