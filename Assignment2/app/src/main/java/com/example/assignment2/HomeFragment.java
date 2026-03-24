package com.example.assignment2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import androidx.appcompat.app.AlertDialog;
import android.widget.ImageView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class HomeFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager2 viewPager2;

    ViewPagerAdapter adapter;
    TabLayoutMediator mediator;

    ImageView menuIcon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);

        menuIcon.setOnClickListener(v->{

            SharedPreferences prefs= requireContext().getSharedPreferences("BookPrefs", Context.MODE_PRIVATE);

            String movie= prefs.getString("movie_name", null);
            int seats= prefs.getInt("seats", -1);
            float price= prefs.getFloat("total_price", -1);

            if (movie == null) {
                new AlertDialog.Builder(requireContext())
                        .setTitle("Last Booking")
                        .setMessage("No previous booking found.")
                        .setPositiveButton("OK", null)
                        .show();
            }
            else {
                String message = "Movie: " + movie + "\nSeats: "+ seats + "\nTotal Price: $" + price;

                new AlertDialog.Builder(requireContext())
                        .setTitle("Last Booking")
                        .setMessage(message)
                        .setPositiveButton("OK", null)
                        .show();
            }


        });

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void init(View view)
    {
        tabLayout= view.findViewById(R.id.tabLayout);
        viewPager2= view.findViewById(R.id.viewPager);
        menuIcon= view.findViewById(R.id.menuIcon);
        adapter = new ViewPagerAdapter(this);

        viewPager2.setAdapter(adapter);

        mediator= new TabLayoutMediator(
                tabLayout,
                viewPager2,
                (tab, position) -> {
                    switch(position)
                    {
                        case 0:
                            tab.setText("Now Showing");
                            break;

                        case 1:
                            tab.setText("Coming Soon");
                            break;

                    }
                }
        );

        mediator.attach();

    }
}

