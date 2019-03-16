package com.filk.dao.jdbc;

import com.filk.dao.GenreDao;
import com.filk.dao.jdbc.mapper.GenreRowMapper;
import com.filk.entity.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcGenreDao implements GenreDao {
    private final String GET_ALL_GENRES_SQL = "SELECT genre_id, name FROM genre ORDER by genre_id";

    private RowMapper<Genre> genreRowMapper = new GenreRowMapper();

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcGenreDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Genre> getAll() {
        return jdbcTemplate.query(GET_ALL_GENRES_SQL, genreRowMapper);
    }
}
