package com.filk.service.impl;

import com.filk.dao.MovieDao;
import com.filk.entity.Movie;
import com.filk.entity.RequestParameters;
import com.filk.service.MovieEnrichmentService;
import com.filk.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultMovieService implements MovieService {
    private MovieDao movieDao;
    private MovieEnrichmentService movieEnrichmentService;

    @Value("${movie.randomCount}")
    private int randomCount;

    @Autowired
    public DefaultMovieService(MovieDao movieDao, MovieEnrichmentService movieEnrichmentService) {
        this.movieDao = movieDao;
        this.movieEnrichmentService = movieEnrichmentService;
    }

    @Override
    public List<Movie> getAll(RequestParameters requestParameters) {
        return movieDao.getAll(requestParameters);
    }

    @Override
    public List<Movie> getRandom() {
        return movieDao.getRandom(randomCount);
    }

    @Override
    public List<Movie> getByGenre(int genreId, RequestParameters requestParameters) {
        return movieDao.getByGenre(genreId, requestParameters);
    }

    @Override
    public Movie getById(int movieId) {
        Movie movie = movieDao.getById(movieId);
        movieEnrichmentService.enrich(movie);

        return movie;
    }
}
