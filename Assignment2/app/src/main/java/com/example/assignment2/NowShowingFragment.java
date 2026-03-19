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
import android.widget.TextView;

import java.util.ArrayList;


public class NowShowingFragment extends Fragment {
    
    
    RecyclerView rvNowShowing;

    ArrayList<Movie> movies;

    MovieAdapter adapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_now_showing, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);

    }

    private void init(View view)
    {
        rvNowShowing= view.findViewById(R.id.rvNowShowing);

        rvNowShowing.setHasFixedSize(true);

        movies= new ArrayList<>();

        adapter= new MovieAdapter(requireContext(), movies, new MovieAdapter.OnBookClickListener() {
            @Override
            public void onBookClick(Movie movie) {
                SeatSelectionFragment fragment = new SeatSelectionFragment();

                Bundle bundle = new Bundle();
                bundle.putString("name-key", movie.getName());
                fragment.setArguments(bundle);

                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        rvNowShowing.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));

        rvNowShowing.setAdapter(adapter);



        movies.add(new Movie(R.drawable.the_dark_knight, "The Dark Knight", "Action / 152 min", "https://www.youtube.com/watch?v=EXeTwQWrcwY"));
        movies.add(new Movie(R.drawable.inception, "Inception", "Sci-Fi / 148 min", "https://www.youtube.com/watch?v=YoHD9XEInc0"));
        movies.add(new Movie(R.drawable.interstellar, "Interstellar", "Adventure / 169 min", "https://www.youtube.com/watch?v=zSWdZVtXT7E"));

    }

}