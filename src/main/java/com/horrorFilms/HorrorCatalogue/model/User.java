package com.horrorFilms.HorrorCatalogue.model;

import java.util.List;

public class User {
    private int user_id;
    private String first_name;
    private String last_name;
    private String email;
    private String password;
    private List<Movie> user_movies;

    public User() {};

    public User(int user_id, String first_name, String last_name, String email, String password, List<Movie> user_movies) {
        this.user_id = user_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.user_movies = user_movies;
    }

    public User(int user_id, String first_name, String last_name, String email, String password) {
        this.user_id = user_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
    }

    public User(String first_name, String last_name, String email, String password) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Movie> getUser_movies() {
        return user_movies;
    }

    public void setUser_movies(List<Movie> user_movies) {
        this.user_movies = user_movies;
    }
}
