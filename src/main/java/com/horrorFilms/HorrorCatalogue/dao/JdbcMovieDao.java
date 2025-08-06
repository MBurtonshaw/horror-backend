package com.horrorFilms.HorrorCatalogue.dao;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.horrorFilms.HorrorCatalogue.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import java.sql.Date;
import java.time.LocalDate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class JdbcMovieDao implements MovieDao {

    private static final Logger logger = LoggerFactory.getLogger(JdbcMovieDao.class);

    private final JdbcTemplate jdbcTemplate;

    // Constructor injection
    public JdbcMovieDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Movie> getMovies() {
        List<Movie> movieList = new ArrayList<>();
        String movieSql = "SELECT movie_id, movie_title, movie_url FROM movie;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(movieSql);

        while (results.next()) {
            int movieId = results.getInt("movie_id");
            String movieTitle = results.getString("movie_title");
            String movieUrl = results.getString("movie_url");
            Movie movie = new Movie(movieId, movieTitle, movieUrl);
            movieList.add(movie);
        }

        return movieList;
    }

    @Override
    public Movie getMovieById(int id) {
        String movieSql = "SELECT m.movie_id, m.movie_title, m.year_released, m.movie_decade, m.movie_url, " +
                "string_agg(DISTINCT g.genre_name, ',') AS genres, " +
                "string_agg(DISTINCT w.writer_name, ',') AS writers, " +
                "string_agg(DISTINCT d.director_name, ',') AS directors, " +
                "json_agg(json_build_object('link_id', l.link_id, 'youtube_link', l.youtube_link, 'prime_link', l.prime_link)) AS links " +
                "FROM movie m " +
                "LEFT JOIN movie_genre mg ON m.movie_id = mg.movie_id " +
                "LEFT JOIN genre g ON mg.genre_name = g.genre_name " +
                "LEFT JOIN movie_writer mw ON m.movie_id = mw.movie_id " +
                "LEFT JOIN writer w ON mw.writer_name = w.writer_name " +
                "LEFT JOIN movie_director md ON m.movie_id = md.movie_id " +
                "LEFT JOIN director d ON md.director_name = d.director_name " +
                "LEFT JOIN movie_links ml ON m.movie_id = ml.movie_id " +
                "LEFT JOIN links l ON ml.link_id = l.link_id " +
                "WHERE m.movie_id = ? " +
                "GROUP BY m.movie_id;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(movieSql, id);

            if (results.next()) {
                Movie movie = mapRowToMovie(results);

                // Parse genres
                String genres = results.getString("genres");
                if (genres != null) {
                    List<Genre> genreList = new ArrayList<>();
                    for (String genreName : genres.split(",")) {
                        genreList.add(new Genre(genreName));
                    }
                    movie.setGenreList(genreList);
                }

                // Parse writers
                String writers = results.getString("writers");
                if (writers != null) {
                    List<Writer> writerList = new ArrayList<>();
                    for (String writerName : writers.split(",")) {
                        writerList.add(new Writer(writerName));
                    }
                    movie.setWriterList(writerList);
                }

                // Parse directors
                String directors = results.getString("directors");
                if (directors != null) {
                    List<Director> directorList = new ArrayList<>();
                    for (String directorName : directors.split(",")) {
                        directorList.add(new Director(directorName));
                    }
                    movie.setDirectorList(directorList);
                }

                // Parse links and remove duplicates
                String linksJson = results.getString("links");
                if (linksJson != null) {
                    Set<Link> linkSet = new HashSet<>(); // Use a Set to filter duplicates
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode linksNode = mapper.readTree(linksJson);

                    for (JsonNode linkNode : linksNode) {
                        int linkId = linkNode.path("link_id").asInt();
                        String youtubeLink = linkNode.path("youtube_link").asText();
                        String primeLink = linkNode.path("prime_link").asText(null); // Default to null if missing

                        Link link = new Link(linkId, youtubeLink, primeLink);
                        linkSet.add(link); // Add to Set to remove duplicates
                    }

                    movie.setLinkList(new ArrayList<>(linkSet)); // Convert Set to List and set it
                }

                return movie;
            }

            return null; // Or throw an exception if a movie not found should be handled differently
        } catch (Exception e) {
            logger.error("Error retrieving movie by id {}: {}", id, e.getMessage());
            throw new RuntimeException("Error retrieving movie", e); // Handle and rethrow exception
        }
    }

    @Override
    public List<Movie> getMoviesByDecade(int decade) {
        List<Movie> moviesByDecade = new ArrayList<>();
        String decadeSql = "SELECT movie_id, movie_title, movie_url FROM movie " +
                            "WHERE movie_decade = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(decadeSql, decade);
        while (results.next()) {
            int movieId = results.getInt("movie_id");
            String movieTitle = results.getString("movie_title");
            String movieUrl = results.getString("movie_url");
            Movie movie = new Movie(movieId, movieTitle, movieUrl);
            moviesByDecade.add(movie);
        }
        return moviesByDecade;
    }

    @Override
    public List<Genre> getGenres() {
        List<Genre> genreList = new ArrayList<>();
        String genreSql = "SELECT genre_name FROM genre;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(genreSql);

        while (results.next()) {
            Genre genre = new Genre();
            genre.setGenre_name(results.getString("genre_name"));
            genreList.add(genre);
        }

        return genreList;
    }

    @Override
    public List<Movie> getMoviesByGenre(Genre genre) {
        List<Movie> moviesByGenre = new ArrayList<>();
        String byGenreSql = "SELECT movie.movie_id, movie.movie_title, movie.movie_url FROM movie JOIN " +
                            "movie_genre ON movie.movie_id = movie_genre.movie_id JOIN " +
                            "genre ON movie_genre.genre_name = genre.genre_name WHERE genre.genre_name = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(byGenreSql, genre.getGenre_name());
        while (results.next()) {
            int movieId = results.getInt("movie_id");
            String movieTitle = results.getString("movie_title");
            String movieUrl = results.getString("movie_url");
            Movie movie = new Movie(movieId, movieTitle, movieUrl);
            moviesByGenre.add(movie);
        }
        return moviesByGenre;
    }

    @Override
    public List<Decade> getDecades() {
        List<Decade> decades = new ArrayList<>();
        String decadeSql = "SELECT decade_name FROM decade;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(decadeSql);
        while (results.next()) {
            Decade decade = new Decade(results.getInt("decade_name"));
            decades.add(decade);
        }
        return decades;
    }

    @Override
    public List<Movie> getMoviesBySeason(String season) {
        List<Movie> seasonalMovies = new ArrayList<>();
        String seasonalSql = "SELECT movie.movie_id, movie.movie_title, movie.movie_url FROM movie JOIN " +
                                "movie_season ON movie.movie_id = movie_season.movie_id JOIN season " +
                                "ON movie_season.season_name = season.season_name WHERE season.season_name = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(seasonalSql, season);
        while (results.next()) {
            int movieId = results.getInt("movie_id");
            String movieTitle = results.getString("movie_title");
            String movieUrl = results.getString("movie_url");
            Movie movie = new Movie(movieId, movieTitle, movieUrl);
            seasonalMovies.add(movie);
        }
        return seasonalMovies;
    }

//    private List<Genre> getGenresForMovie(int movieId) {
//        List<Genre> genresForMovie = new ArrayList<>();
//        String movieGenreSql = "SELECT genre.genre_name from genre JOIN " +
//                "movie_genre ON genre.genre_name = movie_genre.genre_name " +
//                "JOIN movie on movie_genre.movie_id = movie.movie_id WHERE movie.movie_id = ?;";
//        SqlRowSet genreResults = jdbcTemplate.queryForRowSet(movieGenreSql, movieId);
//        while (genreResults.next()) {
//            String genreName = genreResults.getString("genre_name");
//            Genre genre = new Genre(genreName);
//            genresForMovie.add(genre);
//        }
//        return genresForMovie;
//    }
//
//    private List<Writer> getWritersForMovie(int movieId) {
//        List<Writer> writerList = new ArrayList<>();
//        String writerSql = "SELECT writer.writer_name " +
//                "FROM writer JOIN movie_writer ON writer.writer_name = movie_writer.writer_name " +
//                "WHERE movie_writer.movie_id = ?;";
//        SqlRowSet writerResults = jdbcTemplate.queryForRowSet(writerSql, movieId);
//        while (writerResults.next()) {
//            String writerName = writerResults.getString("writer_name");
//            Writer writer = new Writer(writerName);
//            writerList.add(writer);
//        }
//        return writerList;
//    }
//
//    private List<Director> getDirectorsForMovie(int movieId) {
//        List<Director> directorList = new ArrayList<>();
//        String directorSql = "SELECT director.director_name " +
//                "FROM director JOIN movie_director ON director.director_name = movie_director.director_name " +
//                "WHERE movie_director.movie_id = ?;";
//        SqlRowSet directorResults = jdbcTemplate.queryForRowSet(directorSql, movieId);
//        while (directorResults.next()) {
//            String directorName = directorResults.getString("director_name");
//            Director director = new Director(directorName);
//            directorList.add(director);
//        }
//        return directorList;
//    }
//
//    private List<Link> getLinksForMovie(int movieId) {
//        String linkSql = "SELECT links.link_id, links.youtube_link, links.prime_link FROM movie JOIN movie_links ON movie.movie_id = movie_links.movie_id JOIN links ON movie_links.link_id = links.link_id WHERE movie.movie_id = ?;";
//        SqlRowSet results = jdbcTemplate.queryForRowSet(linkSql, movieId);
//        List<Link> linkList = new ArrayList<>();
//        while (results.next()) {
//            int linkId = results.getInt("link_id");
//            String youtubeLink = results.getString("youtube_link");
//            String primeLink = results.getString("prime_link");
//            Link link = new Link(linkId, youtubeLink, primeLink);
//            linkList.add(link);
//        }
//        return linkList;
//    }

    private Movie mapRowToMovie(SqlRowSet results) {
        int movieId = results.getInt("movie_id");
        String movieTitle = results.getString("movie_title");
        LocalDate yearReleased = results.getDate("year_released") != null ? results.getDate("year_released").toLocalDate() : null;
        int movieDecade = results.getInt("movie_decade");
        String movieUrl = results.getString("movie_url");
        return new Movie(movieId, movieTitle, yearReleased, movieDecade, movieUrl);
    }
}