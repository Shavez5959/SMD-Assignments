package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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

import java.util.ArrayList;


public class SeatSelectionFragment extends Fragment {


    TextView storeMovieName,total;

    String movieName, trailer;

    Boolean isReleased;

    View seat1, seat2, seat3, seat4, seat5, seat6, seat7, seat8, seat9, seat10;

    Button btnSnacks, btnBookSeats, btnWatchTrailer, btnComingSoon;

    int selectedSeats = 0;
    int pricePerSeat = 25;

    int totalPrice = 0;

    ArrayList <Integer> selectedseats= new ArrayList<>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_seat_selection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);

        View[] seats = {seat1, seat2, seat3, seat4, seat5, seat6, seat7, seat8, seat9, seat10};
        for (Integer i : selectedseats)
        {
            seats[i].setBackgroundColor(Color.GREEN);
        }

        updateTotal();

        if (getArguments() != null) {
            movieName = getArguments().getString("name-key");
            isReleased = getArguments().getBoolean("isReleased-key", true);
            trailer= getArguments().getString("trailer-key");
            storeMovieName.setText(movieName);
        }

        seat3.setBackgroundColor(Color.RED);
        seat7.setBackgroundColor(Color.RED);

        if (isReleased) {

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


            btnBookSeats.setVisibility(View.VISIBLE);
            btnSnacks.setVisibility(View.VISIBLE);
            btnWatchTrailer.setVisibility(View.GONE);
            btnComingSoon.setVisibility(View.GONE);


            btnBookSeats.setOnClickListener(v -> {
                if (selectedSeats > 0) {
                    TicketSummaryFragment fragment = new TicketSummaryFragment();

                    Bundle bundle = new Bundle();
                    bundle.putString("name-key", movieName);
                    bundle.putInt("seats-key", selectedSeats);
                    bundle.putDouble("tickets-total-key", totalPrice);
                    bundle.putInt("snacks-total-key", 0);

                    fragment.setArguments(bundle);

                    requireActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.navHostFragment, fragment)
                            .addToBackStack(null)
                            .commit();

                    Toast.makeText(requireContext(), "Booking confirmed!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), "Please book a ticket first!", Toast.LENGTH_SHORT).show();
                }

            });


            btnSnacks.setOnClickListener(v -> {

                SnacksFragment fragment = new SnacksFragment();

                Bundle bundle = new Bundle();
                bundle.putString("name-key", movieName);
                bundle.putInt("seats-key", selectedSeats);
                bundle.putDouble("total-key", totalPrice);

                fragment.setArguments(bundle);

                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.navHostFragment, fragment)
                        .addToBackStack(null)
                        .commit();


            });
        }

        else
        {
            btnBookSeats.setVisibility(View.GONE);
            btnSnacks.setVisibility(View.GONE);
            btnComingSoon.setVisibility(View.VISIBLE);
            btnWatchTrailer.setVisibility(View.VISIBLE);

            btnComingSoon.setEnabled(false);

            btnWatchTrailer.setOnClickListener(v->{

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(trailer));
                view.getContext().startActivity(i);
            });

        }

    }


    private void selectSeat(View seat)
    {
        int colour=((ColorDrawable) seat.getBackground()).getColor();

        View[] seats = {seat1, seat2, seat3, seat4, seat5, seat6, seat7, seat8, seat9, seat10};
        int index = -1;

        for (int i = 0; i < seats.length; i++) {
            if (seats[i] == seat) {
                index = i;
                break;
            }
        }

        if (colour==Color.RED)
        {
            Toast.makeText(requireContext(), "This seat is already booked!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (colour==Color.DKGRAY)
        {
            seat.setBackgroundColor(Color.GREEN);
            if (!selectedseats.contains(index))
            {
                selectedseats.add(index);
            }
        }

        if (colour==Color.GREEN)
        {
            seat.setBackgroundColor(Color.DKGRAY);
            selectedseats.remove(Integer.valueOf(index));
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

    private void init(View view)
    {
        storeMovieName=view.findViewById(R.id.storeMovieName);
        seat1 = view.findViewById(R.id.seat1);
        seat2 = view.findViewById(R.id.seat2);
        seat3 = view.findViewById(R.id.seat3);
        seat4 = view.findViewById(R.id.seat4);
        seat5 = view.findViewById(R.id.seat5);
        seat6 = view.findViewById(R.id.seat6);
        seat7 = view.findViewById(R.id.seat7);
        seat8 = view.findViewById(R.id.seat8);
        seat9 = view.findViewById(R.id.seat9);
        seat10 = view.findViewById(R.id.seat10);
        total = view.findViewById(R.id.total);
        btnSnacks= view.findViewById(R.id.btnSnacks);
        btnSnacks.setEnabled(false);
        btnBookSeats=view.findViewById(R.id.btnBookSeats);
        btnWatchTrailer= view.findViewById(R.id.btnWatchTrailer);
        btnComingSoon = view.findViewById(R.id.btnComingSoon);

    }
}