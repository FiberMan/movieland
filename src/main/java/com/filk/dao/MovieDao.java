package com.filk.dao;

import com.filk.entity.Movie;

import java.util.List;
import java.util.Map;

public interface MovieDao {
    List<Movie> getAll(Map<String, Object> requestParameters);
    List<Movie> getRandom(int count);
    List<Movie> getByGenre(Map<String, Object> requestParameters);
}
