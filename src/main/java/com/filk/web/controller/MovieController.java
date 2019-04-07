package com.filk.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.filk.dto.MoviePostDto;
import com.filk.dto.MoviePutDto;
import com.filk.entity.Movie;

import com.filk.util.RequestParameters;
import com.filk.service.MovieService;
import com.filk.util.UserRole;
import com.filk.util.Views;
import com.filk.web.filter.ProtectedBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    @ProtectedBy(userRole = {UserRole.ADMIN})
    public Movie addMovie(@RequestBody MoviePostDto moviePostDto) {
        return movieService.add(moviePostDto);
    }

    @PutMapping("{movieId}")
    @ProtectedBy(userRole = {UserRole.ADMIN})
    public Movie editMovie(@RequestBody MoviePutDto moviePutDto, @PathVariable int movieId) {
        moviePutDto.setMovieId(movieId);
        return movieService.edit(moviePutDto);
    }
}
