package com.horrorFilms.HorrorCatalogue.model;

import java.time.LocalDate;
import java.util.List;

public class Movie {
    private int movie_id;
    private String title;
    private LocalDate year_released;
    private int decade;
    private String movie_url;
    private List<Genre> genreList;
    private List<Writer> writerList;
    private List<Director> directorList;
    private List<Link> linkList;

    public Movie() {}

    public Movie(String title) {
        this.title = title;
    }

    public Movie(int movie_id, String title, LocalDate year_released, int decade, String movie_url, List<Genre> genreList, List<Writer> writerList, List<Director> directorList, List<Link> linkList) {
        this.movie_id = movie_id;
        this.title = title;
        this.year_released = year_released;
        this.decade = decade;
        this.movie_url = movie_url;
        this.genreList = genreList;
        this.writerList = writerList;
        this.directorList = directorList;
        this.linkList = linkList;
    }

    public Movie(int movie_id, String title, LocalDate year_released, int decade, String movie_url, List<Genre> genreList, List<Writer> writerList, List<Director> directorList) {
        this.movie_id = movie_id;
        this.title = title;
        this.year_released = year_released;
        this.decade = decade;
        this.movie_url = movie_url;
        this.genreList = genreList;
        this.writerList = writerList;
        this.directorList = directorList;
    }

    public Movie(int movie_id, String title, LocalDate year_released, int decade, String movie_url) {
        this.movie_id = movie_id;
        this.title = title;
        this.year_released = year_released;
        this.decade = decade;
        this.movie_url = movie_url;
    }

    public Movie(int movie_id, String title, String movie_url) {
        this.movie_id = movie_id;
        this.title = title;
        this.movie_url = movie_url;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getYear_released() {
        return year_released;
    }

    public void setYear_released(LocalDate year_released) {
        this.year_released = year_released;
    }

    public int getDecade() {
        return decade;
    }

    public void setDecade(int decade) {
        this.decade = decade;
    }

    public String getMovie_url() {
        return movie_url;
    }

    public void setMovie_url(String movie_url) {
        this.movie_url = movie_url;
    }

    public List<Genre> getGenreList() {
        return genreList;
    }

    public void setGenreList(List<Genre> genreList) {
        this.genreList = genreList;
    }

    public List<Writer> getWriterList() {
        return writerList;
    }

    public void setWriterList(List<Writer> writerList) {
        this.writerList = writerList;
    }

    public List<Director> getDirectorList() {
        return directorList;
    }

    public void setDirectorList(List<Director> directorList) {
        this.directorList = directorList;
    }

    public List<Link> getLinkList() {
        return linkList;
    }

    public void setLinkList(List<Link> linkList) {
        this.linkList = linkList;
    }

    @Override
    public String toString() {
        return getTitle() + " was released in " + getYear_released() + ", making it a part of the " + getDecade() + "'s";
    }
}
