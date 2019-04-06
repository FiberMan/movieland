package com.filk.service;

import com.filk.entity.Review;
import com.filk.entity.User;
import com.filk.util.RequestReview;

import java.util.List;

public interface ReviewService {
    List<Review> getByMovieId(int movieId);
    Review add(RequestReview requestReview, User user);
}
