package com.filk.service.impl;

import com.filk.dao.GenreDao;
import com.filk.entity.Genre;
import com.filk.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DefaultGenreService implements GenreService {
    private GenreDao genreDao;

    @Value("${genre.cacheUpdatePeriodHours}")
    private long cacheUpdatePeriodHours;
    private LocalDateTime lastUpdatedCache;
    private List<Genre> genresCache;

    @Autowired
    public void setGenreDao(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @Override
    public List<Genre> getAll() {
        if(lastUpdatedCache == null || lastUpdatedCache.plusHours(cacheUpdatePeriodHours).isBefore(LocalDateTime.now())) {
            genresCache = genreDao.getAll();
            lastUpdatedCache = LocalDateTime.now();
        }
        return genresCache;
    }
}
