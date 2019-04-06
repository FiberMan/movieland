package com.filk.service.impl;

import com.filk.entity.Session;
import com.filk.entity.User;
import com.filk.exception.UserBadPassword;
import com.filk.exception.UserNotAuthorized;
import com.filk.service.SecurityService;
import com.filk.service.UserService;
import com.filk.util.RequestCredentials;
import com.filk.util.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@Slf4j
public class DefaultSecurityService implements SecurityService {
    private int sessionLifeTimeHours;
    private CopyOnWriteArrayList<Session> sessions = new CopyOnWriteArrayList<>();
    private UserService userService;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Session login(RequestCredentials requestCredentials) {
        User user = userService.getByEmail(requestCredentials.getEmail());

        if (!passwordEncoder.matches(requestCredentials.getPassword(), user.getHash())) {
            log.debug("Incorrect password [{}] for email [{}] was used.", requestCredentials.getPassword(), requestCredentials.getEmail());
            throw new UserBadPassword("Wrong password");
        }

        // Remove user session (if exists) to recreate it with fresh life time
        removeSession(user);

        Session session = new Session(
                UUID.randomUUID().toString(),
                user,
                LocalDateTime.now().plusHours(sessionLifeTimeHours)
        );

        sessions.add(session);

        return session;
    }

    @Override
    public void logout(String token) {
        removeSession(token);
    }

    @Override
    public Optional<Session> getSession(String token) {
        for (Session session : sessions) {
            if (session.getToken().equals(token)) {
                if (sessionIsAlive(session)) {
                    return Optional.of(session);
                } else {
                    sessions.remove(session);
                    return Optional.empty();
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean checkPermission(User user, List<UserRole> acceptedRoles) {
        return acceptedRoles.isEmpty() || acceptedRoles.contains(user.getRole());
    }

    @Scheduled(fixedDelayString = "${session.cleanupPeriod}", initialDelayString = "${session.cleanupPeriod}")
    public void removeOldSessions() {
        log.info("Cleaning up dead sessions");

        for (Session session : sessions) {
            if (!sessionIsAlive(session)) {
                sessions.remove(session);
            }
        }
    }

    private void removeSession(User user) {
        for (Session session : sessions) {
            if (session.getUser().getId() == user.getId()) {
                sessions.remove(session);
                break;
            }
        }
    }

    private void removeSession(String token) {
        for (Session session : sessions) {
            if (session.getToken().equals(token)) {
                sessions.remove(session);
                break;
            }
        }
    }

    private boolean sessionIsAlive(Session session) {
        return LocalDateTime.now().isBefore(session.getExpireDate());
    }

    @Value("${session.lifeTimeHours}")
    public void setSessionLifeTimeHours(int sessionLifeTimeHours) {
        this.sessionLifeTimeHours = sessionLifeTimeHours;
    }
}
