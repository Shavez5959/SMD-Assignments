package com.example.myapplication;


public class Movie {
    int poster;
    String name;
    String genre;
    String trailer;

    Boolean isReleased;

    public Movie(int poster, String name, String genre, String trailer, Boolean isReleased) {
        this.poster = poster;
        this.name = name;
        this.genre = genre;
        this.trailer = trailer;
        this.isReleased= isReleased;
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

    public Boolean getIsReleased()
    {
        return isReleased;
    }

}
