package com.filk.service.impl;

import com.filk.entity.Country;
import com.filk.entity.Genre;
import com.filk.entity.Movie;
import com.filk.entity.Review;
import com.filk.service.CountryService;
import com.filk.service.GenreService;
import com.filk.service.MovieEnrichmentService;
import com.filk.service.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DefaultMovieEnrichmentService implements MovieEnrichmentService {
    private CountryService countryService;
    private GenreService genreService;
    private ReviewService reviewService;

    @Autowired
    public DefaultMovieEnrichmentService(CountryService countryService, GenreService genreService, ReviewService reviewService) {
        this.countryService = countryService;
        this.genreService = genreService;
        this.reviewService = reviewService;
    }

    public void enrich(Movie movie) {
        log.debug("Getting details for movie ID: {}", movie.getId());

        List<Country> countries = countryService.getByMovieId(movie.getId());
        List<Genre> genres = genreService.getByMovieId(movie.getId());
        List<Review> reviews = reviewService.getByMovieId(movie.getId());

        movie.setCountries(countries);
        movie.setGenres(genres);
        movie.setReviews(reviews);
    }
}
