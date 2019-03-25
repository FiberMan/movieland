package com.filk.service.impl;

import com.filk.dao.CountryDao;
import com.filk.dao.GenreDao;
import com.filk.dao.ReviewDao;
import com.filk.entity.Country;
import com.filk.entity.Genre;
import com.filk.entity.Movie;
import com.filk.entity.Review;
import com.filk.service.MovieEnrichmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DefaultMovieEnrichmentService implements MovieEnrichmentService {
    private CountryDao countryDao;
    private GenreDao genreDao;
    private ReviewDao reviewDao;

    @Autowired
    public DefaultMovieEnrichmentService(CountryDao countryDao, GenreDao genreDao, ReviewDao reviewDao) {
        this.countryDao = countryDao;
        this.genreDao = genreDao;
        this.reviewDao = reviewDao;
    }

    public void enrich(Movie movie) {
        log.debug("Getting details for movie ID: {}", movie.getId());

        List<Country> countries = countryDao.getByMovieId(movie.getId());
        List<Genre> genres = genreDao.getByMovieId(movie.getId());
        List<Review> reviews = reviewDao.getByMovieId(movie.getId());

        movie.setCountries(countries);
        movie.setGenres(genres);
        movie.setReviews(reviews);
    }
}
