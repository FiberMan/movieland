package com.filk.dao.jdbc.mapper;

import com.filk.entity.Genre;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreRowMapper implements RowMapper<Genre> {
    @Override
    public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
        Genre genre = new Genre(rs.getInt("genre_id"), rs.getString("name"));

        return genre;
    }
}
