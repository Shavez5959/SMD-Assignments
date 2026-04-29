package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TicketSummaryFragment extends Fragment {

    TextView tvMovieName, tvTickets, tvSeparatePrice, tvTotalPrice;
    Button btnSendTicket;

    String movieName;
    int ticketsCount;
    double ticketsPrice;
    double snacksPrice;
    double grandTotal;

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ticket_summary, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);

        if (getArguments() != null) {
            movieName = getArguments().getString("name-key");
            ticketsCount = getArguments().getInt("seats-key");
            ticketsPrice = getArguments().getDouble("tickets-total-key");
            snacksPrice = getArguments().getDouble("snacks-total-key");
        }

        grandTotal = ticketsPrice + snacksPrice;

        tvMovieName.setText("Movie: " + movieName);
        tvTickets.setText("No of Tickets: " + ticketsCount);
        tvSeparatePrice.setText(String.format(
                "Tickets Price: %.2f USD \nSnacks Price: %.2f USD",
                ticketsPrice, snacksPrice
        ));
        tvTotalPrice.setText(String.format("%.2f USD", grandTotal));

        btnSendTicket.setOnClickListener(v -> {

            FirebaseAuth auth = FirebaseAuth.getInstance();

            if (auth.getCurrentUser() != null) {

                    String userId = auth.getCurrentUser().getUid();

                    String bookingId = FirebaseDatabase
                            .getInstance("https://my-application-99baa-default-rtdb.asia-southeast1.firebasedatabase.app/")
                            .getReference("bookings")
                            .child(userId)
                            .push()
                            .getKey();

                    Booking booking = new Booking(movieName, ticketsCount, grandTotal);

                    if (bookingId != null) {
                        FirebaseDatabase
                                .getInstance("https://my-application-99baa-default-rtdb.asia-southeast1.firebasedatabase.app/")
                                .getReference("bookings")
                                .child(userId)
                                .child(bookingId)
                                .setValue(booking)
                                .addOnSuccessListener(unused ->
                                        Toast.makeText(requireContext(), "Booking saved!", Toast.LENGTH_SHORT).show()
                                )
                                .addOnFailureListener(e ->
                                        Toast.makeText(requireContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show()
                                );
                    }
                }

             else {
                Toast.makeText(requireContext(), "User not logged in", Toast.LENGTH_SHORT).show();
            }


            String ticketText = "Booking Confirmation: You have booked " + ticketsCount +
                    " ticket(s) for the movie '" + movieName +
                    "'. Ticket Price: " + ticketsPrice +
                    " USD, Snacks Price: " + String.format("%.2f USD", snacksPrice) +
                    ". Grand Total: " + String.format("%.2f USD", grandTotal) +
                    ". Enjoy your show!";

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, ticketText);

            try {
                startActivity(Intent.createChooser(intent, "Share your ticket via"));
            } catch (Exception e) {
                Toast.makeText(requireContext(), "No app available to share the ticket", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init(View view) {
        tvMovieName = view.findViewById(R.id.tvMovieName);
        tvTickets = view.findViewById(R.id.tvTickets);
        tvSeparatePrice = view.findViewById(R.id.tvSeperatePrice);
        tvTotalPrice = view.findViewById(R.id.tvTotalPrice);
        btnSendTicket = view.findViewById(R.id.btnSendTicket);
    }
}