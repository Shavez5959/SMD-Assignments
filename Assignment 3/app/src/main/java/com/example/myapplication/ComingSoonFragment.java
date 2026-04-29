package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ComingSoonFragment extends Fragment {

    RecyclerView rvComingSoon;
    ArrayList<Movie> movies;
    MovieAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_coming_soon, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View view) {

        rvComingSoon = view.findViewById(R.id.rvComingSoon);
        rvComingSoon.setHasFixedSize(true);

        movies = new ArrayList<>();

        adapter = new MovieAdapter(requireContext(), movies, new MovieAdapter.OnBookClickListener() {
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
                        .replace(R.id.navHostFragment, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        rvComingSoon.setLayoutManager(
                new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        );

        rvComingSoon.setAdapter(adapter);

        loadMovies();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadMovies() {

        try {
            JSONArray jsonArray = new JSONArray(loadJSONFromAsset());

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject obj = jsonArray.getJSONObject(i);

                boolean isReleased = obj.getBoolean("isReleased");

                if (!isReleased) {

                    String name = obj.getString("name");
                    String category = obj.getString("category");
                    String trailer = obj.getString("trailer");
                    String imageName = obj.getString("image");

                    int imageResId;

                    switch (imageName) {
                        case "the_dark_knight":
                            imageResId = R.drawable.the_dark_knight;
                            break;
                        case "inception":
                            imageResId = R.drawable.inception;
                            break;
                        case "interstellar":
                            imageResId = R.drawable.interstellar;
                            break;
                        case "hangover":
                            imageResId = R.drawable.hangover;
                            break;
                        case "conjuring":
                            imageResId = R.drawable.conjuring;
                            break;
                        case "shutter_island":
                            imageResId = R.drawable.shutter_island;
                            break;
                        default:
                            imageResId = R.drawable.placeholder;
                            break;
                    }

                    movies.add(new Movie(imageResId, name, category, trailer, false));
                }
            }
            adapter.notifyDataSetChanged();

        } catch (JSONException e) {
            Log.e("MOVIE_JSON_ERROR", "Failed to parse movies JSON", e);
        }
    }

    private String loadJSONFromAsset() {

        String json = null;

        try {
            InputStream is = requireContext().getAssets().open("movies.json");

            int size = is.available();
            byte[] buffer = new byte[size];

            int bytesRead = is.read(buffer);

            is.close();

            if (bytesRead > 0) {
                json = new String(buffer, StandardCharsets.UTF_8);
            }

        } catch (Exception e) {
            Log.e("JSON_ERROR", "Error loading JSON", e);
        }

        return json;
    }
}