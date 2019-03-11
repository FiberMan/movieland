package com.filk.dao.jdbc.mapper;

import com.filk.entity.Movie;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieRowMapper implements RowMapper<Movie> {
    @Override
    public Movie mapRow(ResultSet rs, int rowNum) throws SQLException {
        Movie movie = new Movie();

        movie.setId(rs.getInt("movie_id"));
        movie.setNameRussian(rs.getString("name"));
        movie.setNameNative(rs.getString("name_original"));
        movie.setYearOfRelease(rs.getString("year"));
        movie.setCountry(rs.getString("country"));
        movie.setDescription(rs.getString("description"));
        movie.setPicturePath(rs.getString("poster_url"));
        movie.setRating(rs.getDouble("rating"));
        movie.setPrice(rs.getDouble("price"));

        return movie;
    }
}
