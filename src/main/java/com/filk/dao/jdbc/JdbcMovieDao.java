package com.filk.dao.jdbc;

import com.filk.dao.MovieDao;
import com.filk.dao.jdbc.mapper.MovieRowMapper;
import com.filk.dto.MoviePostDto;
import com.filk.dto.MoviePutDto;
import com.filk.entity.Country;
import com.filk.entity.Genre;
import com.filk.entity.Movie;
import com.filk.util.RequestParameters;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class JdbcMovieDao implements MovieDao {
    private final String GET_ALL_MOVIES = "SELECT movie_id, name, name_original, year, description, poster_url, rating, price FROM movie";
    private final String GET_RANDOM_MOVIES = "SELECT movie_id, name, name_original, year, description, poster_url, rating, price FROM movie order by random() limit ?";
    private final String GET_MOVIES_BY_GENRE = "SELECT m.movie_id, name, name_original, year, description, poster_url, rating, price " +
            "FROM movie m, movie_genre mg " +
            "WHERE m.movie_id = mg.movie_id AND mg.genre_id = ?";
    private final String GET_MOVIE_BY_ID = "SELECT movie_id, name, name_original, year, description, poster_url, rating, price FROM movie WHERE movie_id = ?";

    private final String INSERT_MOVIE = "INSERT INTO movie (name, name_original, year, description, poster_url, price) " +
            "VALUES (:name, :name_original, :year, :description, :poster_url, :price)";
    private final String UPDATE_MOVIE = "UPDATE movie SET name = :name, name_original = :name_original, poster_url = :poster_url WHERE movie_id = :movie_id";

    private final String INSERT_MOVIE_GENRE = "INSERT INTO movie_genre (movie_id, genre_id) VALUES (:movie_id, :genre_id)";
    private final String INSERT_MOVIE_COUNTRY = "INSERT INTO movie_country (movie_id, country_id) VALUES (:movie_id, :country_id)";
    private final String DELETE_MOVIE_GENRE = "DELETE FROM movie_genre WHERE movie_id = :movie_id";
    private final String DELETE_MOVIE_COUNTRY = "DELETE FROM movie_country WHERE movie_id = :movie_id";

    private final RowMapper<Movie> movieRowMapper = new MovieRowMapper();

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedJdbcTemplate;

    @Autowired
    public JdbcMovieDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedJdbcTemplate = namedJdbcTemplate;
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

    @Override
    @Transactional
    public Movie add(MoviePostDto moviePostDto) {
        log.debug("Adding new movie: {}/{}", moviePostDto.getNameRussian(), moviePostDto.getNameNative());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("name", moviePostDto.getNameRussian());
        parameterSource.addValue("name_original", moviePostDto.getNameNative());
        parameterSource.addValue("year", moviePostDto.getYearOfRelease());
        parameterSource.addValue("description", moviePostDto.getDescription());
        parameterSource.addValue("poster_url", moviePostDto.getPicturePath());
        parameterSource.addValue("price", moviePostDto.getPrice());

        namedJdbcTemplate.update(INSERT_MOVIE, parameterSource, keyHolder);
        int movieId = (int) keyHolder.getKey();
        int[] countries = moviePostDto.getCountries();
        int[] genres = moviePostDto.getGenres();

        namedJdbcTemplate.batchUpdate(INSERT_MOVIE_COUNTRY, getMovieCountryParams(movieId, countries));
        namedJdbcTemplate.batchUpdate(INSERT_MOVIE_GENRE, getMovieGenreParams(movieId, genres));

        return convertFromMoviePostDto(moviePostDto, movieId);
    }

    @Override
    @Transactional
    public Movie edit(MoviePutDto moviePutDto) {
        int movieId = moviePutDto.getMovieId();
        int[] countries = moviePutDto.getCountries();
        int[] genres = moviePutDto.getGenres();

        log.debug("Updating movie ID {}", movieId);

        Movie movie = getById(movieId);
        updateFromMoviePutDto(moviePutDto, movie);

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("movie_id", movie.getId());
        parameterSource.addValue("name", movie.getNameRussian());
        parameterSource.addValue("name_original", movie.getNameNative());
        parameterSource.addValue("poster_url", movie.getPicturePath());
        namedJdbcTemplate.update(UPDATE_MOVIE, parameterSource);
        namedJdbcTemplate.update(DELETE_MOVIE_COUNTRY, parameterSource);
        namedJdbcTemplate.update(DELETE_MOVIE_GENRE, parameterSource);
        namedJdbcTemplate.batchUpdate(INSERT_MOVIE_COUNTRY, getMovieCountryParams(movieId, countries));
        namedJdbcTemplate.batchUpdate(INSERT_MOVIE_GENRE, getMovieGenreParams(movieId, genres));

        return movie;
    }

    private MapSqlParameterSource[] getMovieCountryParams(int movieId, int[] countries) {
        MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[countries.length];
        for (int i = 0; i < countries.length; i++) {
            parameterSources[i] =  new MapSqlParameterSource();
            parameterSources[i].addValue("movie_id", movieId);
            parameterSources[i].addValue("country_id", countries[i]);
        }
        return parameterSources;
    }

    private MapSqlParameterSource[] getMovieGenreParams(int movieId, int[] genres) {
        MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[genres.length];
        for (int i = 0; i < genres.length; i++) {
            parameterSources[i] =  new MapSqlParameterSource();
            parameterSources[i].addValue("movie_id", movieId);
            parameterSources[i].addValue("genre_id", genres[i]);
        }
        return parameterSources;
    }

    private Movie convertFromMoviePostDto(MoviePostDto moviePostDto, int movieId) {
        Movie movie = new Movie();

        List<Country> countries = new ArrayList<>();
        for (int countryId : moviePostDto.getCountries()) {
            countries.add(new Country(countryId, ""));
        }

        List<Genre> genres = new ArrayList<>();
        for (int genreId : moviePostDto.getGenres()) {
            genres.add(new Genre(genreId, ""));
        }

        movie.setId(movieId);
        movie.setNameRussian(moviePostDto.getNameRussian());
        movie.setNameNative(moviePostDto.getNameNative());
        movie.setYearOfRelease(moviePostDto.getYearOfRelease());
        movie.setDescription(moviePostDto.getDescription());
        movie.setPicturePath(moviePostDto.getPicturePath());
        movie.setPrice(moviePostDto.getPrice());
        movie.setCountries(countries);
        movie.setGenres(genres);

        return movie;
    }

    private void updateFromMoviePutDto(MoviePutDto moviePutDto, Movie movie) {
        movie.setNameRussian(moviePutDto.getNameRussian());
        movie.setNameNative(moviePutDto.getNameNative());
        movie.setPicturePath(moviePutDto.getPicturePath());
    }

    private String addOrder(RequestParameters requestParameters) {
        if (requestParameters.getSortBy() != null) {
            return " ORDER BY " + requestParameters.getSortBy() + " " + requestParameters.getSortOrder() + ", movie_id";
        }

        return " ORDER BY movie_id";
    }
}
