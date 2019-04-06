package com.filk.web.controller;

import com.filk.entity.Review;
import com.filk.entity.User;
import com.filk.service.ReviewService;
import com.filk.util.RequestReview;
import com.filk.util.UserHolder;
import com.filk.util.UserRole;
import com.filk.web.filter.ProtectedBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReviewController {
    private ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/review")
    @ProtectedBy(userRole = {UserRole.USER})
    public Review addMovieReview(@RequestBody RequestReview requestReview) {
        User user = UserHolder.get();

        return reviewService.add(requestReview, user);
    }
}
