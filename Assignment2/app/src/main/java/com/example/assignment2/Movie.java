package com.example.assignment2;


public class Movie {
    int poster;
    String name;
    String genre;
    String trailer;

    public Movie(int poster, String name, String genre, String trailer) {
        this.poster = poster;
        this.name = name;
        this.genre = genre;
        this.trailer = trailer;
    }

    public int getPoster() {
        return poster;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public String getTrailer() {
        return trailer;
    }

}
