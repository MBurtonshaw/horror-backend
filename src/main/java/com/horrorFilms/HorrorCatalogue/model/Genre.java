package com.horrorFilms.HorrorCatalogue.model;

import java.time.LocalDate;

public class Genre {
    private String genre_name;

    public Genre() {

    }

    public Genre(String genre_name) {
        this.genre_name = genre_name;
    }

    public String getGenre_name() {
        return genre_name;
    }

    public void setGenre_name(String genre_name) {
        this.genre_name = genre_name;
    }
}
