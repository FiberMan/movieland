package com.filk.service;

import com.filk.entity.Movie;
import com.filk.entity.RequestParameters;

import java.util.List;

public interface MovieService {
    List<Movie> getAll(RequestParameters requestParameters);
    List<Movie> getRandom();
    List<Movie> getByGenre(int genreId, RequestParameters requestParameters);
}
