package com.filk.service;

import com.filk.entity.Country;

import java.util.List;

public interface CountryService {
    List<Country> getAll();
    List<Country> getByMovieId(int movieId);
}
