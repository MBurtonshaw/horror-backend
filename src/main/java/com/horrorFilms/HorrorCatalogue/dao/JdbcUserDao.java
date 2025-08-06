package com.horrorFilms.HorrorCatalogue.dao;

import com.horrorFilms.HorrorCatalogue.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.LocalDate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcUserDao implements UserDao {

    private static final Logger logger = LoggerFactory.getLogger(JdbcUserDao.class);

    private final JdbcTemplate jdbcTemplate;

    // Constructor injection
    public JdbcUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User getUser(int userId) {
        String getUserSql = "SELECT consumer_id, first_name, last_name, email, password FROM consumer WHERE consumer_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(getUserSql, userId);
        if (results.next()) {
            User user = mapRowToUser(results);
            user.setUser_movies(getMoviesForUser(user.getUser_id()));
            return user;
        }
        return null; // Return null or throw an exception if user not found
    }

    public List<User> getUsers() {
        String getUserSql = "SELECT consumer_id, first_name, last_name, email, password FROM consumer;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(getUserSql);
        List<User> userList = new ArrayList<>();
        while (results.next()) {
            User user = mapRowToUser(results);
            userList.add(user);
            return userList;
        }
        return null; // Return null or throw an exception if user not found
    }

    private List<Movie> getMoviesForUser(int userId) {
        List<Movie> movieList = new ArrayList<>();
        String mapSql = "SELECT movie.movie_id, movie.movie_title, movie.movie_url " +
                "FROM movie JOIN consumer_movie ON movie.movie_id = consumer_movie.movie_id " +
                "WHERE consumer_movie.consumer_id = ?;";
        SqlRowSet userResults = jdbcTemplate.queryForRowSet(mapSql, userId);
        while (userResults.next()) {
            int newId = userResults.getInt("movie_id");
            String newTitle = userResults.getString("movie_title");
            String newUrl = userResults.getString("movie_url");
            Movie movie = new Movie(newId, newTitle, newUrl);
            movieList.add(movie);
        }
        return movieList;
    }

    public User addUser(User user) {
        String addUserSql = "INSERT INTO consumer (first_name, last_name, email, password) " +
                            "VALUES (?, ?, ?, ?) RETURNING consumer_id;";
        try {
            int newId = jdbcTemplate.queryForObject(addUserSql, int.class, user.getFirst_name(), user.getLast_name(), user.getEmail(), user.getPassword());
            return getUser(newId);
        } catch (CannotGetJdbcConnectionException e) {
            logger.error("Cannot get JDBC connection", e);
        } catch (DataIntegrityViolationException e) {
            logger.error("Data integrity violation", e);
        } catch (Exception e) {
            logger.error("Error adding user", e);
        }
        return null;
    }

    public void deleteUser(int id) {
        String deleteSql = "DELETE FROM consumer WHERE consumer_id = ?;";
        try {
            int rowsAffected = jdbcTemplate.update(deleteSql, id);
            if (rowsAffected == 0) {
                logger.warn("No user found with ID " + id + " to delete.");
            } else {
                logger.info("Successfully deleted user with ID " + id);
            }
        } catch (Exception e) {
            logger.error("Error deleting user with ID " + id, e);
        }
    }

    public void addMovieToUser(int userId, int movieId) {
        String addMovieSql = "INSERT INTO consumer_movie (consumer_id, movie_id) " +
                                "VALUES (?, ?);";
        try {
            jdbcTemplate.update(addMovieSql, userId, movieId);
        } catch (Exception e) {
            logger.error("Error adding movie to list for user ID " + userId, e);
        }
    }

    public void removeMovieFromUser(int userId, int movieId) {
        String deleteMovieSql = "DELETE FROM consumer_movie WHERE consumer_id = ? AND movie_id = ?;";
        try {
            jdbcTemplate.update(deleteMovieSql, userId, movieId);
        } catch (Exception e) {
            logger.error("Error removing movie from list for user ID " + userId, e);
        }
    }

    public List<Movie> getUserMovies(int userId) {
        String userMovieSql = "SELECT movie.movie_id, movie.movie_title, movie.movie_url FROM movie " +
                                "JOIN consumer_movie ON movie.movie_id = consumer_movie.movie_id JOIN " +
                                "consumer ON consumer_movie.consumer_id = consumer.consumer_id WHERE " +
                                "consumer.consumer_id = ?;";
        List<Movie> userMovies = new ArrayList<>();
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(userMovieSql, userId);
            while (results.next()) {
                int id = results.getInt("movie_id");
                String title = results.getString("movie_title");
                String movieUrl = results.getString("movie_url");
                Movie movie = new Movie(id, title, movieUrl);
                userMovies.add(movie);
            }
        } catch (Exception e) {
            logger.error("Error removing movie from list for user ID " + userId, e);
        }
        return userMovies;
    }

    public User loginUser(User user) {
        User confirmedUser = new User();
        try {
            String userSql = "SELECT consumer_id, first_name, last_name, email, password FROM " +
                    "consumer WHERE email = ?;";
            SqlRowSet results = jdbcTemplate.queryForRowSet(userSql, user.getEmail());
            if (results.next()) {
                if (results.getString("password").equals(user.getPassword())) {
                    confirmedUser.setUser_id(results.getInt("consumer_id"));
                    confirmedUser.setFirst_name(results.getString("first_name"));
                    confirmedUser.setLast_name(results.getString("last_name"));
                    confirmedUser.setEmail(results.getString("email"));
                    confirmedUser.setPassword(results.getString("password"));

                    String movieSql = "SELECT movie.movie_id, movie.movie_title, movie.movie_url FROM consumer " +
                            "JOIN consumer_movie ON consumer.consumer_id = consumer_movie.consumer_id " +
                            "JOIN movie ON consumer_movie.movie_id = movie.movie_id WHERE consumer.consumer_id = ?;";
                    SqlRowSet movieResults = jdbcTemplate.queryForRowSet(movieSql, confirmedUser.getUser_id());
                    List<Movie> movieList = new ArrayList<>();
                    while (movieResults.next()) {
                        Movie newMovie = new Movie();
                        newMovie.setMovie_id(movieResults.getInt("movie_id"));
                        newMovie.setTitle(movieResults.getString("movie_title"));
                        newMovie.setMovie_url(movieResults.getString("movie_url"));
                        movieList.add(newMovie);
                        confirmedUser.setUser_movies(movieList);
                    }
                    return confirmedUser;
                }
            }
        } catch (Exception e) {
            logger.error("Error logging in user", e);
        }
        return null;
    }

    private User mapRowToUser(SqlRowSet results) {
        int userId = results.getInt("consumer_id");
        String userFirstName = results.getString("first_name");
        String userLastName = results.getString("last_name");
        String userEmail = results.getString("email");
        String userPassword = results.getString("password");

        List<Movie> movieList = getMoviesForUser(userId);

        return new User(userId, userFirstName, userLastName, userEmail, userPassword, movieList);
    }
}