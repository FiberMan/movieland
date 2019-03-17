package com.filk.web.controller;

import com.filk.entity.Movie;

import com.filk.entity.RequestParameters;
import com.filk.service.MovieService;
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
    public List<Movie> getAllMovies(RequestParameters requestParameters) {
        return movieService.getAll(requestParameters);
    }

    @GetMapping("/random")
    public List<Movie> getRandomMovies() {
        return movieService.getRandom();
    }

    @GetMapping("/genre/{genreId}")
    public List<Movie> getMoviesByGenre(@PathVariable int genreId,
                                        RequestParameters requestParameters) {
        return movieService.getByGenre(genreId, requestParameters);
    }
}
