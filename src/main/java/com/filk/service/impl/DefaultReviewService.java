package com.filk.service.impl;

import com.filk.dao.ReviewDao;
import com.filk.entity.Review;
import com.filk.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultReviewService implements ReviewService {
    private ReviewDao reviewDao;

    @Autowired
    public DefaultReviewService(ReviewDao reviewDao) {
        this.reviewDao = reviewDao;
    }

    @Override
    public List<Review> getByMovieId(int movieId) {
        return reviewDao.getByMovieId(movieId);
    }
}
