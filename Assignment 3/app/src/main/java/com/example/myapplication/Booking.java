package com.example.myapplication;

public class Booking {

    public String movieName;
    public int seats;
    public double totalPrice;

    public Booking() {

    }

    public Booking(String movieName, int seats, double totalPrice) {
        this.movieName = movieName;
        this.seats = seats;
        this.totalPrice = totalPrice;
    }
}