package com.filk.service;

import com.filk.entity.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> getAll();
    List<Genre> getByMovieId(int movieId);
}
