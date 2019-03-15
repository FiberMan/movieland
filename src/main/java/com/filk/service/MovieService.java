package com.filk.service;

import com.filk.entity.Movie;

import java.util.List;
import java.util.Map;

public interface MovieService {
    List<Movie> getAll();
    List<Movie> getAll(Map<String, Object> requestParameters);
    List<Movie> getRandom();
    List<Movie> getByGenre(int genreId);
    List<Movie> getByGenre(Map<String, Object> requestParameters);
}
