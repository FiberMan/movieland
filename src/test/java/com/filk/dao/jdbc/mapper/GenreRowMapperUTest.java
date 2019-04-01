package com.filk.dao.jdbc.mapper;

import com.filk.entity.Genre;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GenreRowMapperUTest {
    @Test
    public void mapRowTest() throws SQLException {
        ResultSet resultSetMock = mock(ResultSet.class);

        when(resultSetMock.getInt("genre_id")).thenReturn(1);
        when(resultSetMock.getString("name")).thenReturn("комедия");

        Genre genre = new GenreRowMapper().mapRow(resultSetMock, 0);

        assertEquals(1, genre.getId());
        assertEquals("комедия", genre.getName());
    }
}
