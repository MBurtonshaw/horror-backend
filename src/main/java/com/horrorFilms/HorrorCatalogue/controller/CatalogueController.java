package com.horrorFilms.HorrorCatalogue.controller;

import com.horrorFilms.HorrorCatalogue.dao.MovieDao;
import com.horrorFilms.HorrorCatalogue.model.Decade;
import com.horrorFilms.HorrorCatalogue.model.Genre;
import com.horrorFilms.HorrorCatalogue.model.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/movies") // Base path for all endpoints in this controller
public class CatalogueController {

    private static final Logger logger = LoggerFactory.getLogger(CatalogueController.class);

    private final MovieDao movieDao;

    public CatalogueController(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    @GetMapping("") // Handles GET requests to /api/movies
    public List<Movie> getMovies() {
        try {
            return movieDao.getMovies();
        } catch (Exception e) {
            logger.error("Error retrieving movies", e); // Log the exception
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to retrieve movies list", e);
        }
    }

    @GetMapping("/{id}") // Handles GET requests to /api/movies
    public Movie getMovieById(@PathVariable int id) {
        try {
            return movieDao.getMovieById(id);
        } catch (Exception e) {
            logger.error("Error retrieving movies", e); // Log the exception
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to retrieve movie by that id", e);
        }
    }

    @GetMapping("/decades")
    public List<Decade> getDecades() {
        try {
            return movieDao.getDecades();
        } catch (Exception e) {
            logger.error("Error retrieving movies", e); // Log the exception
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to retrieve list of decades", e);
        }
    }

    @GetMapping("/decades/{decade}")
    public List<Movie> getMoviesByDecade(@PathVariable int decade) {
        try {
            return movieDao.getMoviesByDecade(decade);
        } catch (Exception e) {
            logger.error("Error retrieving movies", e); // Log the exception
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to retrieve movies by decade", e);
        }
    }

    @GetMapping("/genres")
    public List<Genre> getGenres() {
        try {
            return movieDao.getGenres();
        } catch (Exception e) {
            logger.error("Error retrieving movies", e); // Log the exception
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to retrieve genres list", e);
        }
    }

    @GetMapping("/genres/{genre}")
    public List<Movie> getMoviesByGenre(@PathVariable Genre genre) {
        try {
            return movieDao.getMoviesByGenre(genre);
        } catch (Exception e) {
            logger.error("Error retrieving movies", e); // Log the exception
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to retrieve list of movies by genre", e);
        }
    }

    @GetMapping("/seasons/{season}")
    public List<Movie> getMoviesBySeason(@PathVariable String season) {
        try {
            return movieDao.getMoviesBySeason(season);
        } catch (Exception e) {
            logger.error("Error retrieving movies", e); // Log the exception
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to retrieve list of movies by season", e);
        }
    }
}