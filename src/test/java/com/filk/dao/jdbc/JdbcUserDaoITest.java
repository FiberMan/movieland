package com.filk.dao.jdbc;

import com.filk.config.AppConfig;
import com.filk.dao.UserDao;
import com.filk.entity.User;
import com.filk.exception.UserNotFound;
import com.filk.util.UserRole;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {AppConfig.class}, loader = AnnotationConfigWebContextLoader.class)
public class JdbcUserDaoITest {
    @Autowired
    private UserDao userDao;

    @Test
    public void getByEmailCorrect() {
        User user = userDao.getByEmail("ronald.reynolds66@example.com");

        assertNotNull(user);
        assertEquals(1, user.getId());
        assertEquals("Рональд Рейнольдс", user.getName());
        assertEquals("ronald.reynolds66@example.com", user.getEmail());
        assertEquals(UserRole.USER, user.getRole());
        assertEquals("$2a$10$EWGMITT2oPHOgQNFL5Qnju4H4GqwXxonxjn5p.PkjfPYu3S2sa.qS", user.getHash());
    }

    @Test(expected = UserNotFound.class)
    public void getByEmailIncorrect() {
        User user = userDao.getByEmail("ronald@example.com");
    }
}