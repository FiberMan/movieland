package com.filk.dao.jdbc;

import com.filk.dao.MovieDao;
import com.filk.dao.jdbc.mapper.MovieRowMapper;
import com.filk.entity.Movie;
import com.filk.util.RequestParameters;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class JdbcMovieDao implements MovieDao {
    private final String GET_ALL_MOVIES = "SELECT movie_id, name, name_original, year, description, poster_url, rating, price FROM movie";
    private final String GET_RANDOM_MOVIES = "SELECT movie_id, name, name_original, year, description, poster_url, rating, price FROM movie order by random() limit ?";
    private final String GET_MOVIES_BY_GENRE = "SELECT m.movie_id, name, name_original, year, description, poster_url, rating, price \n" +
            "FROM movie m, movie_genre mg\n" +
            "WHERE m.movie_id = mg.movie_id AND mg.genre_id = ?";
    private final String GET_MOVIE_BY_ID = "SELECT movie_id, name, name_original, year, description, poster_url, rating, price FROM movie WHERE movie_id = ?";

    private final RowMapper<Movie> movieRowMapper = new MovieRowMapper();

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcMovieDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Movie> getAll(RequestParameters requestParameters) {
        log.debug("Getting all movies");
        return jdbcTemplate.query(GET_ALL_MOVIES + addOrder(requestParameters), movieRowMapper);
    }

    @Override
    public List<Movie> getRandom(int count) {
        log.debug("Getting random movies");
        return jdbcTemplate.query(GET_RANDOM_MOVIES, movieRowMapper, count);
    }

    @Override
    public List<Movie> getByGenre(int genreId, RequestParameters requestParameters) {
        log.debug("Getting movies by genre ID: {}", genreId);
        return jdbcTemplate.query(GET_MOVIES_BY_GENRE + addOrder(requestParameters), movieRowMapper, genreId);
    }

    @Override
    public Movie getById(int movieId) {
        log.debug("Getting movie by ID: {}", movieId);
        return jdbcTemplate.queryForObject(GET_MOVIE_BY_ID, movieRowMapper, movieId);
    }

    private String addOrder(RequestParameters requestParameters) {
        if (requestParameters.getSortBy() != null) {
            return " ORDER BY " + requestParameters.getSortBy() + " " + requestParameters.getSortOrder() + ", movie_id";
        }

        return " ORDER BY movie_id";
    }
}
