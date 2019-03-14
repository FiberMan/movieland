package com.filk.web.controller;

import com.filk.entity.Movie;

import com.filk.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MovieController {
    private MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/movie")
    public List<Movie> getAllMovies(@RequestParam(name = "sort", required = false) String sortBy,
                                    @RequestParam(name = "order", required = false) String sortOrder) {
        Map<String, Object> requestParams = new HashMap<>();
        requestParams.put("sortBy", sortBy);
        requestParams.put("sortOrder", sortOrder);

        return movieService.getAll(requestParams);
    }

    @GetMapping("/movie/random")
    public List<Movie> getRandomMovies() {
        return movieService.getRandom();
    }

    @GetMapping("/movie/genre/{genreId}")
    public List<Movie> getMoviesByGenre(@PathVariable int genreId,
                                        @RequestParam(name = "sort", required = false) String sortBy,
                                        @RequestParam(name = "order", required = false) String sortOrder) {
        Map<String, Object> requestParams = new HashMap<>();
        requestParams.put("genreId", genreId);
        requestParams.put("sortBy", sortBy);
        requestParams.put("sortOrder", sortOrder);

        return movieService.getByGenre(requestParams);
    }
}
