package com.filk.service.impl;

import com.filk.dao.MovieDao;
import com.filk.dto.MoviePostDto;
import com.filk.dto.MoviePutDto;
import com.filk.entity.Movie;
import com.filk.util.RequestParameters;
import com.filk.service.CurrencyService;
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
    private CurrencyService currencyService;

    @Value("${movie.randomCount}")
    private int randomCount;

    @Autowired
    public DefaultMovieService(MovieDao movieDao, MovieEnrichmentService movieEnrichmentService, CurrencyService currencyService) {
        this.movieDao = movieDao;
        this.movieEnrichmentService = movieEnrichmentService;
        this.currencyService = currencyService;
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
    public Movie getById(int movieId, RequestParameters requestParameters) {
        Movie movie = movieDao.getById(movieId);
        movieEnrichmentService.enrich(movie);

        movie.setPrice(currencyService.convert(movie.getPrice(), requestParameters.getCurrency()));

        return movie;
    }

    @Override
    public Movie add(MoviePostDto moviePostDto) {
        return movieDao.add(moviePostDto);
    }

    @Override
    public Movie edit(MoviePutDto moviePutDto) {
        return movieDao.edit(moviePutDto);
    }
}
