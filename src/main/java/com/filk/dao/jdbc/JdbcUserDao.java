package com.filk.dao.jdbc;

import com.filk.dao.UserDao;
import com.filk.dao.jdbc.mapper.UserRowMapper;
import com.filk.entity.User;
import com.filk.exception.UserNotFound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class JdbcUserDao implements UserDao {
    private static final String GET_USER_BY_EMAIL = "SELECT user_id, name, email, role, hash FROM \"user\" WHERE email = ?";

    private JdbcTemplate jdbcTemplate;
    private UserRowMapper userRowMapper = new UserRowMapper();

    @Autowired
    public JdbcUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User getByEmail(String email) {
        log.debug("Getting user for email {}", email);
        try {
            return jdbcTemplate.queryForObject(GET_USER_BY_EMAIL, userRowMapper, email);
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFound("User not found", e);
        }
    }
}
