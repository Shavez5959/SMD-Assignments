package com.example.assignment1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ticket_summary extends AppCompatActivity {

    TextView tvMovieName, tvTickets, tvSeperatePrice, tvTotalPrice;
    Button btnSendTicket;

    String movieName;
    int ticketsCount;
    int ticketsPrice;
    double snacksPrice;
    double grandTotal;

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ticket_summary_activity);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ticket), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();

        Intent i = getIntent();
        movieName = i.getStringExtra("movie-key");
        ticketsCount = i.getIntExtra("tickets-key", 0);
        ticketsPrice = i.getIntExtra("tickets-total-key", 0);
        snacksPrice = i. getDoubleExtra("snacks-total-key", 0.0);


        grandTotal = ticketsPrice + snacksPrice;


        tvMovieName.setText("Movie: " + movieName);
        tvTickets.setText("No of Tickets: " + ticketsCount);
        tvSeperatePrice.setText(String.format("Tickets Price: %d USD \nSnacks Price: %.2f USD", ticketsPrice, snacksPrice));
        tvTotalPrice.setText(String.format("%.2f USD", grandTotal));

        btnSendTicket.setOnClickListener(v -> {
            String ticketText = "Booking Confirmation: You have booked " + ticketsCount + " ticket(s) for the movie '"
                    + movieName + "'. Ticket Price: " + ticketsPrice + " USD, Snacks Price: "
                    + String.format("%.2f USD", snacksPrice) + ". Grand Total: " + String.format("%.2f USD", grandTotal)
                    + ". Enjoy your show!";

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, ticketText);


            try {
                startActivity(Intent.createChooser(intent, "Share your ticket via"));
            } catch (Exception e) {
                Toast.makeText(ticket_summary.this, "No app available to share the ticket", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void init()
    {
        tvMovieName = findViewById(R.id.tvMovieName);
        tvTickets = findViewById(R.id.tvTickets);
        tvSeperatePrice = findViewById(R.id.tvSeperatePrice);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        btnSendTicket = findViewById(R.id.btnSendTicket);
    }
}
