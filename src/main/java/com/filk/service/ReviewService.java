package com.filk.service;

import com.filk.entity.Review;

import java.util.List;

public interface ReviewService {
    List<Review> getByMovieId(int movieId);
}
