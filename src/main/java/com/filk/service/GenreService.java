package com.filk.service;

import com.filk.dao.GenreDao;
import com.filk.entity.Genre;

import java.util.List;

public interface GenreService {
    void setGenreDao(GenreDao genreDao);
    List<Genre> getAll();
}
