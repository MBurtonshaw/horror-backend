package com.horrorFilms.HorrorCatalogue.model;

import java.time.LocalDate;

public class Decade {
    private int decade_name;

    public Decade() {

    }

    public Decade(int decade_name) {
        this.decade_name = decade_name;
    }

    public int getDecade_name() {
        return decade_name;
    }

    public void setDecade_name(int decade_name) {
        this.decade_name = decade_name;
    }
}
