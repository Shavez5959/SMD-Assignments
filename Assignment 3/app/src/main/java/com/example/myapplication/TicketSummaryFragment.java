package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.annotation.*;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

public class TicketSummaryFragment extends Fragment {

    TextView tvMovieName, tvTickets, tvSeparatePrice, tvTotalPrice;
    Button btnSendTicket;

    String movieName;
    int ticketsCount;
    double ticketsPrice;
    double snacksPrice;
    double grandTotal;

    int poster;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ticket_summary, container, false);
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);

        if (getArguments() != null) {
            movieName = getArguments().getString("name-key");
            ticketsCount = getArguments().getInt("seats-key");
            ticketsPrice = getArguments().getDouble("tickets-total-key");
            snacksPrice = getArguments().getDouble("snacks-total-key");
            poster = getArguments().getInt("poster-key");
        }

        grandTotal = ticketsPrice + snacksPrice;

        tvMovieName.setText("Movie: " + movieName);
        tvTickets.setText("Tickets: " + ticketsCount);
        tvSeparatePrice.setText("Tickets: " + ticketsPrice + " USD\n"+ "Snacks: " + snacksPrice+ " USD");
        tvTotalPrice.setText(String.format("Total: %.2f USD", grandTotal));

        btnSendTicket.setOnClickListener(v -> saveBooking());
    }

    private void saveBooking() {

        FirebaseAuth auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() == null) {
            Toast.makeText(requireContext(), "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = auth.getCurrentUser().getUid();

        DatabaseReference ref = FirebaseDatabase.getInstance(
                "https://my-application-99baa-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("bookings")
                .child(userId);

        String bookingId = ref.push().getKey();

        Booking booking = new Booking(
                poster,
                movieName,
                ticketsCount,
                grandTotal
        );

        if (bookingId != null) {
            ref.child(bookingId).setValue(booking)
                    .addOnSuccessListener(unused ->
                            Toast.makeText(requireContext(), "Booking Saved", Toast.LENGTH_SHORT).show()
                    )
                    .addOnFailureListener(e ->
                            Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_LONG).show()
                    );
        }

        @SuppressLint("DefaultLocale") String ticketText = "Booking Confirmation: You have booked " + ticketsCount + " ticket(s) for the movie '"
                + movieName + "'. Ticket Price: " + ticketsPrice + " USD, Snacks Price: "
                + String.format("%.2f USD", snacksPrice) + ". Grand Total: " + String.format("%.2f USD", grandTotal)
                + ". Enjoy your show!";

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, ticketText);

        startActivity(Intent.createChooser(intent, "Share Ticket"));
    }

    private void init(View view) {
        tvMovieName = view.findViewById(R.id.tvMovieName);
        tvTickets = view.findViewById(R.id.tvTickets);
        tvSeparatePrice = view.findViewById(R.id.tvSeperatePrice);
        tvTotalPrice = view.findViewById(R.id.tvTotalPrice);
        btnSendTicket = view.findViewById(R.id.btnSendTicket);
    }
}