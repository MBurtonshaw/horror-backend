package com.horrorFilms.HorrorCatalogue.dao;

import com.horrorFilms.HorrorCatalogue.model.Decade;
import com.horrorFilms.HorrorCatalogue.model.Genre;
import com.horrorFilms.HorrorCatalogue.model.Movie;

import java.util.List;

public interface MovieDao {
    List<Movie> getMovies();
    List<Movie> getMoviesByDecade(int decade);
    Movie getMovieById(int id);
    List<Genre> getGenres();
    List<Movie> getMoviesByGenre(Genre genre);
    List<Decade> getDecades();
    List<Movie> getMoviesBySeason(String season);
}
