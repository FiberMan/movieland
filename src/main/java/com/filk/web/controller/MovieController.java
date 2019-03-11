package com.filk.web.controller;

import com.filk.entity.Movie;

import com.filk.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MovieController {
    private MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/api/v1/movie")
    public List<Movie> getAllMovies() {
        return movieService.getAll();
    }
}
