package com.filk.dao.jdbc.mapper;

import com.filk.entity.User;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserRowMapperTest {

    @Test
    public void mapRow() throws SQLException {
        ResultSet resultSetMock = mock(ResultSet.class);

        when(resultSetMock.getInt("user_id")).thenReturn(1);
        when(resultSetMock.getString("name")).thenReturn("Тузик");
        when(resultSetMock.getString("email")).thenReturn("tuzik@gmail.com");
        when(resultSetMock.getString("role")).thenReturn("USER");
        when(resultSetMock.getString("hash")).thenReturn("kljsdhfk");
        when(resultSetMock.getString("salt")).thenReturn("83h8hfkswjeh");

        User user = new UserRowMapper().mapRow(resultSetMock, 0);

        assertNotNull(user);
        assertEquals(1, user.getId());
        assertEquals("Тузик", user.getName());
        assertEquals("tuzik@gmail.com", user.getEmail());
        assertEquals("USER", user.getRole().toString());
        assertEquals("kljsdhfk", user.getHash());
        assertEquals("83h8hfkswjeh", user.getSalt());
    }
}