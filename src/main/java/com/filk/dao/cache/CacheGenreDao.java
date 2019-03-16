package com.filk.dao.cache;

import com.filk.dao.GenreDao;
import com.filk.entity.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;

@Repository
@Primary
public class CacheGenreDao implements GenreDao {
    private GenreDao genreDao;
    private volatile List<Genre> genresCache;

    @Value("${genre.cacheUpdatePeriodHours}")
    private final long cacheUpdatePeriodHours = 2;

    @Autowired
    public void setGenreDao(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @Override
    public List<Genre> getAll() {
        return genresCache;
    }

    @PostConstruct
    @Scheduled(fixedDelay = cacheUpdatePeriodHours * 60 * 60 * 1000)
    public void refreshGenresCache() {
        genresCache = genreDao.getAll();
    }
}
