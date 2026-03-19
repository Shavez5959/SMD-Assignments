package com.example.assignment2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class ComingSoonFragment extends Fragment {

    RecyclerView rvComingSoon;

    ArrayList<Movie> movies;

    MovieAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_coming_soon, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);

    }

    private void init(View view)
    {
        rvComingSoon= view.findViewById(R.id.rvComingSoon);

        rvComingSoon.setHasFixedSize(true);

        movies= new ArrayList<>();

        adapter= new MovieAdapter(requireContext(), movies, new MovieAdapter.OnBookClickListener() {
            @Override
            public void onBookClick(Movie movie) {
                SeatSelectionFragment fragment = new SeatSelectionFragment();

                Bundle bundle = new Bundle();
                bundle.putString("name-key", movie.getName());
                bundle.putBoolean("isReleased-key", movie.getIsReleased());
                bundle.putString("trailer-key", movie.getTrailer());
                fragment.setArguments(bundle);

                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        rvComingSoon.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));

        rvComingSoon.setAdapter(adapter);

        movies.add(new Movie(R.drawable.hangover, "The Hangover ", "Comedy / 100 min", "https://www.youtube.com/watch?v=tlize92ffnY", false));
        movies.add(new Movie(R.drawable.conjuring, "The Conjuring", "Horror / 112 min", "https://www.youtube.com/watch?v=ejMMn0t58Lc", false));
        movies.add(new Movie(R.drawable.shutter_island, "Shutter Island", "Mystery / 148 min", "https://www.youtube.com/watch?v=v8yrZSkKxTA", false));

    }

}