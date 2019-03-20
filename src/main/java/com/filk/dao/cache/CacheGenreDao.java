package com.filk.dao.cache;

import com.filk.dao.GenreDao;
import com.filk.entity.Genre;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Repository
@Primary
@Slf4j
public class CacheGenreDao implements GenreDao {
    private GenreDao genreDao;
    private volatile List<Genre> genresCache;

    @Autowired
    public void setGenreDao(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @Override
    public List<Genre> getAll() {
        return new ArrayList<>(genresCache);
    }

    @PostConstruct
    @Scheduled(fixedDelayString = "${genre.cacheUpdatePeriod}", initialDelayString = "${genre.cacheUpdatePeriod}")
    public void refreshGenresCache() {
        genresCache = genreDao.getAll();
        log.debug("Genres cache has been refreshed. Current genres count: {}", genresCache.size());
    }
}
