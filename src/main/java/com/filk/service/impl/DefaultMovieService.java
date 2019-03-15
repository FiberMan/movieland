package com.filk.service.impl;

import com.filk.dao.MovieDao;
import com.filk.entity.Movie;
import com.filk.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<Movie> getAll() {
        return movieDao.getAll();
    }

    @Override
    public List<Movie> getAll(Map<String, Object> requestParameters) {
        return movieDao.getAll(requestParameters);
    }

    @Override
    public List<Movie> getRandom() {
        return movieDao.getRandom(randomCount);
    }

    @Override
    public List<Movie> getByGenre(int genreId) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("genreId", genreId);

        return getByGenre(parameters);
    }

    @Override
    public List<Movie> getByGenre(Map<String, Object> requestParameters) {
        return movieDao.getByGenre(requestParameters);
    }
}
