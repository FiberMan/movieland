package com.filk.dao;

import com.filk.entity.Country;

import java.util.List;

public interface CountryDao {
    List<Country> getByMovieId(int movieId);
}
