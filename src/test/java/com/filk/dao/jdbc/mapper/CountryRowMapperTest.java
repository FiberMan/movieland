package com.filk.dao.jdbc.mapper;

import com.filk.entity.Country;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CountryRowMapperTest {
    @Test
    public void mapRow() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);

        when(resultSet.getInt("country_id")).thenReturn(1);
        when(resultSet.getString("name")).thenReturn("Україна");

        Country country = new CountryRowMapper().mapRow(resultSet, 0);

        assertNotNull(country);
        assertEquals(1, country.getId());
        assertEquals("Україна", country.getName());
    }
}
