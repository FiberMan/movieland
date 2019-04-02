package com.filk.dao.jdbc.mapper;

import com.filk.entity.User;
import com.filk.util.UserRole;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(
                rs.getInt("user_id"),
                rs.getString("name"),
                rs.getString("email"),
                UserRole.valueOf(rs.getString("role")),
                rs.getString("hash"),
                rs.getString("salt")
        );
    }
}