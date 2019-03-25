package com.filk.dao.jdbc.mapper;

import com.filk.entity.Review;
import com.filk.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewRowMapper implements RowMapper<Review> {
    @Override
    public Review mapRow(ResultSet rs, int rowNum) throws SQLException {
        Review review = new Review();
        User user = new User();

        user.setId(rs.getInt("user_id"));
        user.setName(rs.getString("user_name"));

        review.setId(rs.getInt("review_id"));
        review.setText(rs.getString("text"));
        review.setUser(user);

        return review;
    }
}
