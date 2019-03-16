package com.filk.dao;

import com.filk.entity.Movie;

import java.util.List;

public interface MovieDao {
    List<Movie> getAll();
    List<Movie> getRandom(int count);
    List<Movie> getByGenre(int genreId);
}
