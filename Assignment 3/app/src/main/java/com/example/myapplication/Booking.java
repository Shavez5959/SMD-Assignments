package com.example.myapplication;

public class Booking {

    public String id;
    public int poster;
    public String movieName;
    public int seats;
    public double totalPrice;

    public Booking() {}

    public Booking(int poster, String movieName, int seats, double totalPrice) {
        this.poster = poster;
        this.movieName = movieName;
        this.seats = seats;
        this.totalPrice = totalPrice;
    }
}