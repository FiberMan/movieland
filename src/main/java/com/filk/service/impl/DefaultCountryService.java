package com.filk.service.impl;

import com.filk.dao.CountryDao;
import com.filk.entity.Country;
import com.filk.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultCountryService implements CountryService {
    private CountryDao countryDao;

    @Autowired
    public DefaultCountryService(CountryDao countryDao) {
        this.countryDao = countryDao;
    }

    @Override
    public List<Country> getAll() {
        return countryDao.getAll();
    }

    @Override
    public List<Country> getByMovieId(int movieId) {
        return countryDao.getByMovieId(movieId);
    }
}
