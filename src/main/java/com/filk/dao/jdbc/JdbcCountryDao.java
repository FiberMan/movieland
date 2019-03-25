package com.filk.dao.jdbc;

import com.filk.dao.CountryDao;
import com.filk.dao.jdbc.mapper.CountryRowMapper;
import com.filk.entity.Country;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class JdbcCountryDao implements CountryDao {
    private static final String GET_REVIEWS_BY_MOVIE_ID = "SELECT c.country_id, c.name FROM movie_country mc, country c " +
            "WHERE mc.country_id = c.country_id AND mc.movie_id = ?";

    private JdbcTemplate jdbcTemplate;
    private CountryRowMapper countryRowMapper = new CountryRowMapper();

    @Autowired
    public JdbcCountryDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Country> getByMovieId(int movieId) {
        log.debug("Getting countries for movie ID: {}", movieId);
        return jdbcTemplate.query(GET_REVIEWS_BY_MOVIE_ID, countryRowMapper, movieId);
    }
}
