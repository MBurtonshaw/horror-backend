package com.horrorFilms.HorrorCatalogue.model;

import java.util.Objects;

public class Link {
    private int link_id;
    private String youtube_link;
    private String prime_link;

    // Default constructor
    public Link() {
    }

    // Parameterized constructor
    public Link(int link_id, String youtube_link, String prime_link) {
        this.link_id = link_id;
        this.youtube_link = youtube_link;
        this.prime_link = prime_link;
    }

    // Getters and Setters
    public int getLink_id() {
        return link_id;
    }

    public void setLink_id(int link_id) {
        this.link_id = link_id;
    }

    public String getYoutube_link() {
        return youtube_link;
    }

    public void setYoutube_link(String youtube_link) {
        this.youtube_link = youtube_link;
    }

    public String getPrime_link() {
        return prime_link;
    }

    public void setPrime_link(String prime_link) {
        this.prime_link = prime_link;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Link link = (Link) o;
        return link_id == link.link_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(link_id);
    }

    @Override
    public String toString() {
        return "Link{" +
                "link_id=" + link_id +
                ", youtube_link='" + youtube_link + '\'' +
                ", prime_link='" + prime_link + '\'' +
                '}';
    }
}