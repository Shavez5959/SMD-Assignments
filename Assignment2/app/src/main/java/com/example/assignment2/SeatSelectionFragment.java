package com.example.assignment2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class SeatSelectionFragment extends Fragment {

    TextView tvMovieName;


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

        if (getArguments() != null) {
            String movieName = getArguments().getString("name-key");
            tvMovieName.setText(movieName);
        }

    }

    private void init(View view)
    {
        tvMovieName=view.findViewById(R.id.storeMovieName);

    }

}