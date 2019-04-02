package com.filk.service.impl;

import com.filk.entity.Session;
import com.filk.entity.User;
import com.filk.service.UserService;
import com.filk.util.RequestCredentials;
import com.filk.util.UserRole;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DefaultSecurityServiceTest {
    private UserService userServiceMock = mock(UserService.class);
    private DefaultSecurityService securityService = new DefaultSecurityService(userServiceMock);
    private Session session;

    @Before
    public void setup() {
        User user = new User(
                1,
                "User Name",
                "ronald.reynolds66@example.com",
                UserRole.USER,
                "$2a$10$mwlQOO42GQJ54kbUBpoHfOrrIrXnm0JcP3ryNo.1gS79DPv5mUKy6",
                "jzyG/8hU8sc2apD0JuU1fQ=="
        );

        when(userServiceMock.getByEmail("ronald.reynolds66@example.com")).thenReturn(user);

        securityService.setSessionLifeTimeHours(2);
        session = securityService.login(new RequestCredentials("ronald.reynolds66@example.com", "paco"));
    }

    @Test
    public void login() {
        assertTrue(session.isLive());
        assertNotNull(session.getToken());
        assertEquals("User Name", session.getUser().getName());
    }

    @Test
    public void logout() {
        String token = session.getToken();
        securityService.logout(token);

        assertFalse(securityService.getValidSession(token).isPresent());
    }
}