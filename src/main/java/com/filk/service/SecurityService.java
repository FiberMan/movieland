package com.filk.service;

import com.filk.entity.Session;
import com.filk.entity.User;
import com.filk.util.RequestCredentials;
import com.filk.util.UserRole;

import java.util.List;
import java.util.Optional;

public interface SecurityService {
    Session login(RequestCredentials requestCredentials);
    void logout(String token);
    Optional<Session> getSession(String token);
    boolean checkPermission(User user, List<UserRole> acceptedRoles);
}
