package com.horrorFilms.HorrorCatalogue;

import com.horrorFilms.HorrorCatalogue.dao.JdbcMovieDao;
import com.horrorFilms.HorrorCatalogue.dao.JdbcUserDao;
import com.horrorFilms.HorrorCatalogue.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserTests {

    private JdbcTemplate jdbcTemplate;
    private JdbcUserDao jdbcUserDao;

    @BeforeEach
    void setUp() {
        // Configure the data source to connect to your existing database
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/TestHorrorFilms");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres1");

        // Initialize JdbcTemplate with your existing data source
        jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcUserDao = new JdbcUserDao(jdbcTemplate);

        // Optionally, you can set up test data or ensure the test environment is correctly configured
    }

    @Test
    void testGetUser() {
        User user = jdbcUserDao.getUser(1);
        Assertions.assertEquals(1, user.getUser_id());
        Assertions.assertEquals("Matthew", user.getFirst_name());
        Assertions.assertEquals("Burtonshaw", user.getLast_name());
        Assertions.assertEquals("MBurtonshaw@gmail.com", user.getEmail());
        Assertions.assertEquals("password", user.getPassword());
    }

    @Test
    void testGetMoviesForUser() {
        User user = jdbcUserDao.getUser(1);
        Assertions.assertEquals(5, user.getUser_movies().size());
        Assertions.assertEquals(3, user.getUser_movies().get(0).getMovie_id());
        Assertions.assertEquals("Ginger Snaps", user.getUser_movies().get(0).getTitle());
        Assertions.assertEquals("ginger_snaps", user.getUser_movies().get(0).getMovie_url());

        Assertions.assertEquals(25, user.getUser_movies().get(4).getMovie_id());
        Assertions.assertEquals("Halloween", user.getUser_movies().get(4).getTitle());
        Assertions.assertEquals("halloween", user.getUser_movies().get(4).getMovie_url());
    }

    @Test
    void testGetUsers() {
        List<User> userList = jdbcUserDao.getUsers();
        Assertions.assertEquals("Matthew", userList.get(0).getFirst_name());
    }

    @Test
    void testAddAndDeleteUser() {
        User user = new User("Tim", "Tam", "TimTamTheGreat@gmail.com", "password");
        int newUserId = jdbcUserDao.addUser(user).getUser_id();
        User currentUser = jdbcUserDao.getUser(newUserId);
        Assertions.assertNotNull(currentUser, "User should be added successfully");
        Assertions.assertEquals(user.getFirst_name(), currentUser.getFirst_name(), "First names should match");
        Assertions.assertEquals(user.getFirst_name(), currentUser.getFirst_name(), "Last names should match");
        Assertions.assertEquals(user.getEmail(), currentUser.getEmail(), "Emails should match");

        jdbcUserDao.deleteUser(newUserId);
        User deletedUser = jdbcUserDao.getUser(newUserId);
        Assertions.assertNull(deletedUser, "User should be null after deletion");
    }

    @Test
    void testGetUserMovies() {
        Movie Pontypool = jdbcUserDao.getUserMovies(1).get(1);
        Movie Halloween = jdbcUserDao.getUserMovies(1).get(4);

        Assertions.assertEquals("Pontypool", Pontypool.getTitle());
        Assertions.assertEquals("halloween", Halloween.getMovie_url());
    }

    @Test
    void testAddMovieToUserAndDeleteMovieFromUser() {
        User user = jdbcUserDao.getUser(1);
        List<Movie> userMovies = user.getUser_movies();
        Assertions.assertEquals(5, userMovies.size());

        jdbcUserDao.addMovieToUser(1, 5);
        User updatedUser = jdbcUserDao.getUser(1);
        List<Movie> newUserMovies = updatedUser.getUser_movies();
        Assertions.assertEquals(6, newUserMovies.size());

        jdbcUserDao.removeMovieFromUser(1, 5);
        User deletingUser = jdbcUserDao.getUser(1);
        Assertions.assertEquals(5, deletingUser.getUser_movies().size());
    }

    @Test
    void testLoginUser() {
        User user = new User("Matthew", "Burtonshaw", "MBurtonshaw@gmail.com", "password");
        User confirmedHero = jdbcUserDao.loginUser(user);
        Assertions.assertNotNull(confirmedHero);
        Assertions.assertEquals("MBurtonshaw@gmail.com", confirmedHero.getEmail());
        Assertions.assertEquals("password", confirmedHero.getPassword());
    }


}