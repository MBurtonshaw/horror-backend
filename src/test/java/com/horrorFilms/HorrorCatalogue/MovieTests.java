package com.horrorFilms.HorrorCatalogue;

import com.horrorFilms.HorrorCatalogue.dao.JdbcMovieDao;
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

public class MovieTests {

    private JdbcTemplate jdbcTemplate;
    private JdbcMovieDao jdbcMovieDao;

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
        jdbcMovieDao = new JdbcMovieDao(jdbcTemplate);

        // Optionally, you can set up test data or ensure the test environment is correctly configured
    }

    @Test
    void testGetMovies() {
        List<Movie> movieList = jdbcMovieDao.getMovies();
        Assertions.assertEquals(35, movieList.size());

        Assertions.assertEquals(1, movieList.get(0).getMovie_id());
        Assertions.assertEquals("The Sitter", movieList.get(0).getTitle());
        Assertions.assertEquals("the_sitter", movieList.get(0).getMovie_url());

        Assertions.assertEquals(12, movieList.get(11).getMovie_id());
        Assertions.assertEquals("Black Christmas", movieList.get(11).getTitle());
        Assertions.assertEquals("black_christmas", movieList.get(11).getMovie_url());

        Assertions.assertEquals(34, movieList.get(33).getMovie_id());
        Assertions.assertEquals("Death Proof", movieList.get(33).getTitle());
        Assertions.assertEquals("death_proof", movieList.get(33).getMovie_url());
    }

    @Test
    void testGetMovie() {
        Movie movie1 = jdbcMovieDao.getMovieById(1);
        Movie movie2 = jdbcMovieDao.getMovieById(12);
        Movie movie3 = jdbcMovieDao.getMovieById(23);
        Movie movie4 = jdbcMovieDao.getMovieById(34);
        Movie movie5 = jdbcMovieDao.getMovieById(8);
        Movie movie6 = jdbcMovieDao.getMovieById(18);

        Assertions.assertEquals("The Sitter", movie1.getTitle());
        Assertions.assertEquals("Black Christmas", movie2.getTitle());
        Assertions.assertEquals("Evil Dead 2: Dead by Dawn", movie3.getTitle());
        Assertions.assertEquals(2000, movie4.getDecade());
        Assertions.assertEquals(1970, movie2.getDecade());

        List<Writer> writerList = movie5.getWriterList();
        List<Writer> anotherWriterList = movie2.getWriterList();
        Assertions.assertEquals("Stephen King", writerList.get(0).getWriter_name());
        Assertions.assertEquals("Frank Darabont", writerList.get(1).getWriter_name());
        Assertions.assertEquals("A. Roy Moore", anotherWriterList.get(0).getWriter_name());

        List<Director> directorList = movie2.getDirectorList();
        List<Director> directorsList = movie6.getDirectorList();
        Assertions.assertEquals("Bob Clark", directorList.get(0).getDirector_name());
        Assertions.assertEquals("Ti West", directorsList.get(2).getDirector_name());

        List<Genre> genreList = movie2.getGenreList();
        List<Genre> genreList2 = movie5.getGenreList();

        Assertions.assertEquals("Suburban", genreList.get(0).getGenre_name());
        Assertions.assertEquals(4, genreList2.size());

        LocalDate localDate = LocalDate.parse("1977-11-05");
        LocalDate localDate2 = LocalDate.parse("2012-10-05");
        Assertions.assertEquals(localDate, movie1.getYear_released());
        Assertions.assertEquals(localDate2, movie6.getYear_released());

        Assertions.assertEquals(1, movie1.getMovie_id());
        Assertions.assertEquals(12, movie2.getMovie_id());
        Assertions.assertEquals("the_sitter", movie1.getMovie_url());
        Assertions.assertEquals("black_christmas", movie2.getMovie_url());
    }

    @Test
    void testGetMoviesByDecade() {
        List<Movie> seventiesMovies = jdbcMovieDao.getMoviesByDecade(1970);
        Assertions.assertEquals(5, seventiesMovies.size());
        Assertions.assertEquals(6, seventiesMovies.get(1).getMovie_id());
        Assertions.assertEquals("Shivers", seventiesMovies.get(1).getTitle());
        Assertions.assertEquals("shivers", seventiesMovies.get(1).getMovie_url());

        List<Movie> ninetiesMovies = jdbcMovieDao.getMoviesByDecade(1990);
        Assertions.assertEquals(3, ninetiesMovies.size());
        Assertions.assertEquals(27, ninetiesMovies.get(2).getMovie_id());
        Assertions.assertEquals("Scream", ninetiesMovies.get(2).getTitle());
        Assertions.assertEquals("scream", ninetiesMovies.get(2).getMovie_url());
    }

    @Test
    void testGetGenres() {
        List<Genre> genres = jdbcMovieDao.getGenres();
        Assertions.assertEquals(19, genres.size());
        Assertions.assertEquals("Eerie", genres.get(0).getGenre_name());
        Assertions.assertEquals("Body", genres.get(7).getGenre_name());
    }

    @Test
    void testGetMoviesByGenre() {
        List<Movie> eerieMovies = jdbcMovieDao.getMoviesByGenre(new Genre("Eerie"));
        Assertions.assertEquals(14, eerieMovies.size());
        Assertions.assertEquals(1, eerieMovies.get(0).getMovie_id());
        Assertions.assertEquals("The Sitter", eerieMovies.get(0).getTitle());
        Assertions.assertEquals("the_sitter", eerieMovies.get(0).getMovie_url());
        Assertions.assertEquals(25, eerieMovies.get(9).getMovie_id());
        Assertions.assertEquals("Halloween", eerieMovies.get(9).getTitle());
        Assertions.assertEquals("halloween", eerieMovies.get(9).getMovie_url());

        List<Movie> campyMovies = jdbcMovieDao.getMoviesByGenre(new Genre("Campy"));
        Assertions.assertEquals(10, campyMovies.size());
        Assertions.assertEquals(9, campyMovies.get(0).getMovie_id());
        Assertions.assertEquals("From Dusk till Dawn", campyMovies.get(0).getTitle());
        Assertions.assertEquals("dusk_till_dawn", campyMovies.get(0).getMovie_url());
        Assertions.assertEquals(28, campyMovies.get(5).getMovie_id());
        Assertions.assertEquals("Re-Animator", campyMovies.get(5).getTitle());
        Assertions.assertEquals("re_animator", campyMovies.get(5).getMovie_url());
    }

    @Test
    void testGetDecades() {
        List<Decade> decades = jdbcMovieDao.getDecades();
        Assertions.assertEquals(8, decades.size());
        Assertions.assertEquals(1950, decades.get(0).getDecade_name());
        Assertions.assertEquals(2020, decades.get(7).getDecade_name());
    }

    @Test
    void testGetMoviesBySeason() {
        List<Movie> fallMovies = jdbcMovieDao.getMoviesBySeason("Fall");
        Assertions.assertEquals(6, fallMovies.size());
        Assertions.assertEquals(2, fallMovies.get(0).getMovie_id());
        Assertions.assertEquals("It Follows", fallMovies.get(0).getTitle());
        Assertions.assertEquals("it_follows", fallMovies.get(0).getMovie_url());
        Assertions.assertEquals(25, fallMovies.get(4).getMovie_id());
        Assertions.assertEquals("Halloween", fallMovies.get(4).getTitle());
        Assertions.assertEquals("halloween", fallMovies.get(4).getMovie_url());

        List<Movie> summerMovies = jdbcMovieDao.getMoviesBySeason("Summer");
        Assertions.assertEquals(6, summerMovies.size());
        Assertions.assertEquals(1, summerMovies.get(0).getMovie_id());
        Assertions.assertEquals("The Sitter", summerMovies.get(0).getTitle());
        Assertions.assertEquals("the_sitter", summerMovies.get(0).getMovie_url());
        Assertions.assertEquals(21, summerMovies.get(3).getMovie_id());
        Assertions.assertEquals("Texas Chainsaw Massacre", summerMovies.get(3).getTitle());
        Assertions.assertEquals("texas_chainsaw_massacre", summerMovies.get(3).getMovie_url());
    }

}