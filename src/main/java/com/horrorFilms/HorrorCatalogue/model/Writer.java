package com.horrorFilms.HorrorCatalogue.model;

public class Writer {
    private String writer_name;
    public Writer() {};
    public Writer(String writer_name) {
        this.writer_name = writer_name;
    }

    public String getWriter_name() {
        return writer_name;
    }

    public void setWriter_name(String writer_name) {
        this.writer_name = writer_name;
    }
}
