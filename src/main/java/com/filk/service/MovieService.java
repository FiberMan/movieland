package com.filk.service;

import com.filk.entity.Movie;

import java.util.List;

public interface MovieService {
    List<Movie> getAll();
    List<Movie> getRandom();
}
