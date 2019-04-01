package com.filk.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.filk.entity.Movie;

import com.filk.util.RequestParameters;
import com.filk.service.MovieService;
import com.filk.util.Views;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/movie")
public class MovieController {
    private MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    @JsonView(Views.Movie.class)
    public List<Movie> getAllMovies(RequestParameters requestParameters) {
        return movieService.getAll(requestParameters.postProcess());
    }

    @GetMapping("/random")
    @JsonView(Views.Movie.class)
    public List<Movie> getRandomMovies() {
        return movieService.getRandom();
    }

    @GetMapping("/genre/{genreId}")
    @JsonView(Views.Movie.class)
    public List<Movie> getMoviesByGenre(@PathVariable int genreId,
                                        RequestParameters requestParameters) {
        return movieService.getByGenre(genreId, requestParameters.postProcess());
    }

    @GetMapping("{movieId}")
    @JsonView(Views.MovieDetail.class)
    public Movie getMovieById(@PathVariable int movieId,
                              RequestParameters requestParameters) {
        return movieService.getById(movieId, requestParameters.postProcess());
    }
}
