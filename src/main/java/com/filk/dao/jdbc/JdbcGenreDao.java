package com.filk.dao.jdbc;

import com.filk.dao.GenreDao;
import com.filk.dao.jdbc.mapper.GenreRowMapper;
import com.filk.entity.Genre;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class JdbcGenreDao implements GenreDao {
    private final String GET_ALL_GENRES_SQL = "SELECT genre_id, name FROM genre ORDER by genre_id";
    private final String GET_GENRES_BY_MOVIE_ID = "SELECT g.genre_id, g.name FROM movie_genre mg, genre g " +
            "WHERE mg.genre_id = g.genre_id AND mg.movie_id = ?";

    private RowMapper<Genre> genreRowMapper = new GenreRowMapper();

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcGenreDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Genre> getAll() {
        log.debug("Getting all genres");
        return jdbcTemplate.query(GET_ALL_GENRES_SQL, genreRowMapper);
    }

    @Override
    public List<Genre> getByMovieId(int movieId) {
        log.debug("Getting genres for movie ID: {}", movieId);
        return jdbcTemplate.query(GET_GENRES_BY_MOVIE_ID, genreRowMapper, movieId);
    }
}
