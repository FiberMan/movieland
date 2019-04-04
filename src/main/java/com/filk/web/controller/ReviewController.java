package com.filk.web.controller;

import com.filk.entity.Review;
import com.filk.entity.Session;
import com.filk.service.ReviewService;
import com.filk.util.RequestReview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ReviewController {
    private ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/review")
    public Review addMovieReview(@RequestBody RequestReview requestReview, HttpServletRequest httpServletRequest) {
        Session session = (Session) httpServletRequest.getAttribute("session");

        return reviewService.add(requestReview, session.getUser());
    }
}
