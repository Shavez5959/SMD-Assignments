package com.example.assignment1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class seat_selection extends AppCompatActivity {

    TextView storeMovieName,total;

    String movieName;

    View seat1, seat2, seat3, seat4, seat5, seat6, seat7, seat8, seat9, seat10;

    Button btnSnacks, btnBookSeats;

    int selectedSeats = 0;
    int pricePerSeat = 25;

    int totalPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.seat_selection_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.seat_selection), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();

        Intent intent = getIntent();

        movieName = "";

        if (intent.hasExtra("movie1-key")) {
            movieName = intent.getStringExtra("movie1-key");
        }
        else if (intent.hasExtra("movie2-key")) {
            movieName = intent.getStringExtra("movie2-key");
        }
        else  {
            movieName = intent.getStringExtra("movie3-key");
        }

        storeMovieName.setText(movieName);

        seat3.setBackgroundColor(Color.RED);
        seat7.setBackgroundColor(Color.RED);

        seat1.setOnClickListener(v -> selectSeat(seat1));
        seat2.setOnClickListener(v -> selectSeat(seat2));
        seat3.setOnClickListener(v -> selectSeat(seat3));
        seat4.setOnClickListener(v -> selectSeat(seat4));
        seat5.setOnClickListener(v -> selectSeat(seat5));
        seat6.setOnClickListener(v -> selectSeat(seat6));
        seat7.setOnClickListener(v -> selectSeat(seat7));
        seat8.setOnClickListener(v -> selectSeat(seat8));
        seat9.setOnClickListener(v -> selectSeat(seat9));
        seat10.setOnClickListener(v -> selectSeat(seat10));



        btnBookSeats.setOnClickListener(v-> {
            if (selectedSeats>0)
            {
                Intent i = new Intent(seat_selection.this, ticket_summary.class);
                i.putExtra("movie-key", movieName);
                i.putExtra("tickets-key", selectedSeats);
                i.putExtra("tickets-total-key", totalPrice);
                i.putExtra("snacks-total-key", 0);
                startActivity(i);
            }
            else
            {
                Toast.makeText(this, "Please book a ticket first!", Toast.LENGTH_SHORT).show();
            }
        });

        btnSnacks.setOnClickListener(v->{
            Intent i = new Intent(seat_selection.this, snacks.class);
            i.putExtra("movie-key", movieName);
            i.putExtra("tickets-key", selectedSeats);
            i.putExtra("total-key", totalPrice);
            startActivity(i);
        });


    }

    private void selectSeat(View seat)
    {
        int colour=((ColorDrawable) seat.getBackground()).getColor();

        if (colour==Color.RED)
        {
            Toast.makeText(this, "This seat is already booked!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (colour==Color.DKGRAY)
        {
            seat.setBackgroundColor(Color.GREEN);
        }

        if (colour==Color.GREEN)
        {
            seat.setBackgroundColor(Color.DKGRAY);
        }

        updateTotal();



    }

    @SuppressLint("SetTextI18n")
    private void updateTotal()
    {
        selectedSeats = 0;

        View[] seats = {seat1, seat2, seat3, seat4, seat5, seat6, seat7, seat8, seat9, seat10};
        for (View s : seats) {
            int color = ((ColorDrawable) s.getBackground()).getColor();
            if (color == Color.GREEN) {
                selectedSeats++;
            }
        }

        totalPrice = selectedSeats * pricePerSeat;


        total.setText("Seats: " + selectedSeats + " | Total: $" + totalPrice);

        btnSnacks.setEnabled(selectedSeats > 0);
    }

    private void init()
    {
        storeMovieName=findViewById(R.id.storeMovieName);
        seat1 = findViewById(R.id.seat1);
        seat2 = findViewById(R.id.seat2);
        seat3 = findViewById(R.id.seat3);
        seat4 = findViewById(R.id.seat4);
        seat5 = findViewById(R.id.seat5);
        seat6 = findViewById(R.id.seat6);
        seat7 = findViewById(R.id.seat7);
        seat8 = findViewById(R.id.seat8);
        seat9 = findViewById(R.id.seat9);
        seat10 = findViewById(R.id.seat10);
        total = findViewById(R.id.total);
        btnSnacks=findViewById(R.id.btnSnacks);
        btnSnacks.setEnabled(false);
        btnBookSeats=findViewById(R.id.btnBookSeats);
    }
}
