package com.filk.dao;

import com.filk.entity.Movie;
import com.filk.util.RequestParameters;

import java.util.List;

public interface MovieDao {
    List<Movie> getAll(RequestParameters requestParameters);
    List<Movie> getRandom(int count);
    List<Movie> getByGenre(int genreId, RequestParameters requestParameters);
    Movie getById(int movieId);
}
