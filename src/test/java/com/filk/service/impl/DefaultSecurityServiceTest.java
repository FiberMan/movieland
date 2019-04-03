package com.filk.service.impl;

import com.filk.entity.Session;
import com.filk.entity.User;
import com.filk.service.UserService;
import com.filk.util.RequestCredentials;
import com.filk.util.UserRole;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DefaultSecurityServiceTest {
    private UserService userServiceMock = mock(UserService.class);
    private DefaultSecurityService securityService = new DefaultSecurityService(userServiceMock);
    private Session session;

    @Before
    public void setup() {
        User user = User.newBuilder()
                .setId(1)
                .setName("User Name")
                .setEmail("ronald.reynolds66@example.com")
                .setRole(UserRole.USER)
                .setHash("$2a$10$EWGMITT2oPHOgQNFL5Qnju4H4GqwXxonxjn5p.PkjfPYu3S2sa.qS")
                .build();

        when(userServiceMock.getByEmail("ronald.reynolds66@example.com")).thenReturn(user);

        securityService.setSessionLifeTimeHours(2);
        session = securityService.login(new RequestCredentials("ronald.reynolds66@example.com", "paco"));
    }

    @Test
    public void login() {
        assertTrue(session.getExpireDate().isAfter(LocalDateTime.now()));
        assertNotNull(session.getToken());
        assertEquals("User Name", session.getUser().getName());
    }

    @Test
    public void logout() {
        String token = session.getToken();
        securityService.logout(token);

        assertFalse(securityService.getSession(token).isPresent());
    }
}