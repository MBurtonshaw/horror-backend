package com.horrorFilms.HorrorCatalogue.model;

public class Director {
    private String director_name;
    public Director() {};
    public Director(String director_name) {
        this.director_name = director_name;
    }

    public String getDirector_name() {
        return director_name;
    }

    public void setDirector_name(String director_name) {
        this.director_name = director_name;
    }
}
