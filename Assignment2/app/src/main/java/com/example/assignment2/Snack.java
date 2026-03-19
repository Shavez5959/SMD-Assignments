package com.example.assignment2;

import android.widget.ImageView;
import android.widget.TextView;

public class Snack {
    int image;
    String name;

    String description;

    String price;



    public Snack(int image, String name, String description, String price) {
        this.image = image;
        this.name = name;
        this.description = description;
        this.price = price;
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

    public String getPrice() {
        return price;
    }
}
