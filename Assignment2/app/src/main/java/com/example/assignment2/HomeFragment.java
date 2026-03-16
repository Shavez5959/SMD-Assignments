package com.example.assignment2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class HomeFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager2 viewPager2;

    ViewPagerAdapter adapter;
    TabLayoutMediator mediator;

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
    }

    private void init(View view)
    {
        tabLayout= view.findViewById(R.id.tabLayout);
        viewPager2= view.findViewById(R.id.viewPager);
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

