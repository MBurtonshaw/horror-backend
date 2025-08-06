package com.horrorFilms.HorrorCatalogue.dao;

import com.horrorFilms.HorrorCatalogue.model.Decade;
import com.horrorFilms.HorrorCatalogue.model.Genre;
import com.horrorFilms.HorrorCatalogue.model.Movie;
import com.horrorFilms.HorrorCatalogue.model.User;

import java.util.List;

public interface UserDao {
    User getUser(int id);
    User addUser(User user);
    void deleteUser(int id);
    void addMovieToUser(int userId, int movieId);
    void removeMovieFromUser(int userId, int movieId);
    List<Movie> getUserMovies(int userId);
    User loginUser(User user);
}
