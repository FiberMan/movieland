package com.filk.dao.jdbc.mapper;

import com.filk.entity.Movie;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MovieRowMapperTest {

    @Test
    public void mapRowTest() throws SQLException {
        ResultSet resultSetMock = mock(ResultSet.class);

        when(resultSetMock.getInt("movie_id")).thenReturn(1);
        when(resultSetMock.getString("name")).thenReturn("Кино");
        when(resultSetMock.getString("name_original")).thenReturn("Movie");
        when(resultSetMock.getString("year")).thenReturn("2020");
        when(resultSetMock.getString("country")).thenReturn("Ukraine");
        when(resultSetMock.getString("description")).thenReturn("Самое лучшее кино");
        when(resultSetMock.getString("poster_url")).thenReturn("https://url.to.poster.com/p.jpg");
        when(resultSetMock.getDouble("rating")).thenReturn(9.33);
        when(resultSetMock.getDouble("price")).thenReturn(188.22);

        Movie movie = new MovieRowMapper().mapRow(resultSetMock, 0);

        assert movie != null;
        assertEquals(1, movie.getId());
        assertEquals("Кино", movie.getNameRussian());
        assertEquals("Movie", movie.getNameNative());
        assertEquals("2020", movie.getYearOfRelease());
        assertEquals("Ukraine", movie.getCountry());
        assertEquals("Самое лучшее кино", movie.getDescription());
        assertEquals("https://url.to.poster.com/p.jpg", movie.getPicturePath());
        assertEquals(9.33, movie.getRating(), 0.01);
        assertEquals(188.22, movie.getPrice(), 0.01);
    }
}
