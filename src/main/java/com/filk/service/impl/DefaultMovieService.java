package com.filk.service.impl;

import com.filk.dao.MovieDao;
import com.filk.entity.Movie;
import com.filk.entity.RequestParameters;
import com.filk.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultMovieService implements MovieService {
    private MovieDao movieDao;

    @Value("${movie.randomCount}")
    private int randomCount;

    @Autowired
    public DefaultMovieService(MovieDao movieDao) {
        this.movieDao = movieDao;
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
}
