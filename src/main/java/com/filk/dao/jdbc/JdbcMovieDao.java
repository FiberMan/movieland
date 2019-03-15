package com.filk.dao.jdbc;

import com.filk.dao.MovieDao;
import com.filk.dao.jdbc.mapper.MovieRowMapper;
import com.filk.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcMovieDao implements MovieDao {
    private final String GET_ALL_MOVIES = "SELECT movie_id, name, name_original, year, country, description, poster_url, rating, price FROM movie";
    private final String GET_RANDOM_MOVIES = "SELECT movie_id, name, name_original, year, country, description, poster_url, rating, price FROM movie order by random() limit ?";
    private final String GET_MOVIES_BY_GENRE = "SELECT m.movie_id, name, name_original, year, country, description, poster_url, rating, price \n" +
            "FROM movie m, movie_genre mg\n" +
            "WHERE m.movie_id = mg.movie_id AND mg.genre_id = ?";

    private final RowMapper<Movie> movieRowMapper = new MovieRowMapper();

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcMovieDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Movie> getAll() {
        return getAll(new HashMap<>());
    }

    @Override
    public List<Movie> getAll(Map<String, Object> requestParameters) {
        return jdbcTemplate.query(GET_ALL_MOVIES + addOrder(requestParameters), movieRowMapper);
    }

    @Override
    public List<Movie> getRandom(int count) {
        return jdbcTemplate.query(GET_RANDOM_MOVIES, movieRowMapper, count);
    }

    @Override
    public List<Movie> getByGenre(int genreId) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("genreId", genreId);

        return getByGenre(parameters);
    }

    @Override
    public List<Movie> getByGenre(Map<String, Object> requestParameters) {
        return jdbcTemplate.query(GET_MOVIES_BY_GENRE + addOrder(requestParameters), movieRowMapper, requestParameters.get("genreId"));
    }

    private String addOrder(Map<String, Object> requestParameters) {
        String sortBy = (String) requestParameters.get("sortBy");
        String sortOrder = (String) requestParameters.get("sortOrder");

        if ("rating".equals(sortBy)) {
            if ("asc".equals(sortOrder)) {
                return " ORDER BY rating ASC";
            } else {
                return " ORDER BY rating DESC";
            }
        }

        if ("price".equals(sortBy)) {
            if ("desc".equals(sortOrder)) {
                return " ORDER BY price DESC";
            } else {
                return " ORDER BY price ASC";
            }
        }

        return " ORDER BY movie_id";
    }
}
