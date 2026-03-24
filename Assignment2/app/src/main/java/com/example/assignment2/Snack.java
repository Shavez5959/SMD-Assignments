package com.example.assignment2;

import android.widget.ImageView;
import android.widget.TextView;

public class Snack {
    int image;
    String name;

    String description;

    Double price;

    int quantity;



    public Snack(int image, String name, String description, Double price, int quantity) {
        this.image = image;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity= quantity;
    }

    public int getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

