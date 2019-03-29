package com.filk.dao.jdbc.mapper;

import com.filk.entity.Review;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReviewRowMapperUTest {

    @Test
    public void mapRow() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);

        when(resultSet.getInt("review_id")).thenReturn(1);
        when(resultSet.getString("text")).thenReturn("Описание фильма.");
        when(resultSet.getInt("user_id")).thenReturn(20);
        when(resultSet.getString("user_name")).thenReturn("Вася Пупыч");

        Review review = new ReviewRowMapper().mapRow(resultSet, 0);

        assertNotNull(review);
        assertEquals(1, review.getId());
        assertEquals("Описание фильма.", review.getText());

        assertNotNull(review.getUser());
        assertEquals(20, review.getUser().getId());
        assertEquals("Вася Пупыч", review.getUser().getName());
    }
}