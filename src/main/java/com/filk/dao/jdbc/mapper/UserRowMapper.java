package com.filk.dao.jdbc.mapper;

import com.filk.entity.User;
import com.filk.util.UserRole;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return User.newBuilder()
                .setId(rs.getInt("user_id"))
                .setName(rs.getString("name"))
                .setEmail(rs.getString("email"))
                .setRole(UserRole.valueOf(rs.getString("role")))
                .setHash(rs.getString("hash"))
                .build();
    }
}