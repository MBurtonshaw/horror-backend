package com.horrorFilms.HorrorCatalogue.controller;

import com.horrorFilms.HorrorCatalogue.dao.UserDao;
import com.horrorFilms.HorrorCatalogue.model.Movie;
import com.horrorFilms.HorrorCatalogue.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/users") // Base path for all endpoints in this controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserDao userDao;

    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable int id) {
        try {
            User user = userDao.getUser(id);
            if (user == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            }
            return user;
        } catch (Exception e) {
            logger.error("Error retrieving user with ID {}", id, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to retrieve user by id", e);
        }
    }

    @PostMapping("/new")
    public User addUser(@RequestBody User user) {
        try {
            return userDao.addUser(user);
        } catch (Exception e) {
            logger.error("Error adding user", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to add user", e);
        }
    }

    @DeleteMapping("/{id}/delete")
    public void deleteUser(@PathVariable int id) {
        try {
            userDao.deleteUser(id);
        } catch (Exception e) {
            logger.error("Error deleting user with ID {}", id, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to delete user", e);
        }
    }

    @PostMapping("/{userId}/addMovie")
    public ResponseEntity<Void> addMovieToUser(@PathVariable int userId, @RequestBody int movieId) {
        try {
            userDao.addMovieToUser(userId, movieId);
            return ResponseEntity.noContent().build(); // Ensures a valid HTTP response
        } catch (Exception e) {
            logger.error("Error adding movie with ID {} from user with ID {}", movieId, userId, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to remove movie from user", e);
        }
    }

    @DeleteMapping("/{userId}/removeMovie")
    public ResponseEntity<Void> removeMovieFromUser(@PathVariable int userId, @RequestBody int movieId) {
        try {
            userDao.removeMovieFromUser(userId, movieId);
            return ResponseEntity.noContent().build(); // Ensures a valid HTTP response
        } catch (Exception e) {
            logger.error("Error removing movie with ID {} from user with ID {}", movieId, userId, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to remove movie from user", e);
        }
    }

    @GetMapping("/{userId}/movieList")
    public List<Movie> getUserMovies(@PathVariable int userId) {
        try {
            return userDao.getUserMovies(userId);
        } catch (Exception e) {
            logger.error("Error retrieving movies for user with ID {}", userId, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to retrieve movies for user", e);
        }
    }

    @PostMapping("/login")
    public User loginUser(@RequestBody User user) {
        try {
            return userDao.loginUser(user);
        } catch (Exception e) {
            logger.error("Error loggin in user {}", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to retrieve movies for user", e);
        }
    }

}