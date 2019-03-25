package com.filk.service;

import com.filk.entity.Movie;
import org.springframework.stereotype.Service;

@Service
public interface MovieEnrichmentService {
    void enrich(Movie movie);
}
