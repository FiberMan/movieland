package com.filk.dao.jdbc;

import com.filk.dao.ReviewDao;
import com.filk.dao.jdbc.mapper.ReviewRowMapper;
import com.filk.entity.Movie;
import com.filk.entity.Review;
import com.filk.entity.User;
import com.filk.util.RequestReview;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class JdbcReviewDao implements ReviewDao {
    private static final String GET_REVIEWS_BY_MOVIE_ID = "SELECT r.review_id, r.movie_id, r.text, u.user_id, u.name as user_name " +
            "FROM review r, \"user\" u WHERE r.user_id = u.user_id AND r.movie_id = ?";
    private static final String INSERT_REVIEW = "INSERT INTO review (movie_id, user_id, text) VALUES (:movie_id, :user_id, :text)";

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedJdbcTemplate;
    private ReviewRowMapper reviewRowMapper = new ReviewRowMapper();

    @Autowired
    public JdbcReviewDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedJdbcTemplate = namedJdbcTemplate;
    }

    @Override
    public List<Review> getByMovieId(int movieId) {
        log.debug("Getting reviews by Movie ID: {}", movieId);
        return jdbcTemplate.query(GET_REVIEWS_BY_MOVIE_ID, reviewRowMapper, movieId);
    }

    @Override
    public Review add(RequestReview requestReview, User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("movie_id", requestReview.getMovieId());
        parameterSource.addValue("text", requestReview.getText());
        parameterSource.addValue("user_id", user.getId());

        namedJdbcTemplate.update(INSERT_REVIEW, parameterSource, keyHolder, new String[]{"review_id"});

        Movie movie = new Movie();
        movie.setId(requestReview.getMovieId());

        Review review = new Review();
        review.setId((int) keyHolder.getKey());
        review.setMovie(movie);
        review.setText(requestReview.getText());
        review.setUser(user);

        return review;
    }
}