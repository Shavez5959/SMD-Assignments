package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class SnacksFragment extends Fragment {

    TextView totalPrice;

    Button btnReset, btnConfirm;

    ListView lvSnacks;

    ArrayList <Snack> snacks;

    SnackAdapter snackAdapter;

    double total;
    String movieName;
    int ticketsCount;
    double ticketsPrice;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_snacks, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);

        if (getArguments()!=null)
        {
            movieName=getArguments().getString("name-key");
            ticketsCount=getArguments().getInt("seats-key");
            ticketsPrice=getArguments().getDouble("total-key");

        }

        snackAdapter.setOnQuantityChangeListener(this::updateTotalPrice);

        btnReset.setOnClickListener(v-> {
            for (Snack s : snacks) {
                s.setQuantity(0);
            }
            snackAdapter.notifyDataSetChanged();
            updateTotalPrice();
        });

        btnConfirm.setOnClickListener(v->{
            TicketSummaryFragment fragment = new TicketSummaryFragment();

            Bundle bundle = new Bundle();
            bundle.putString("name-key", movieName);
            bundle.putInt("seats-key", ticketsCount);
            bundle.putDouble("tickets-total-key", ticketsPrice);
            bundle.putDouble("snacks-total-key", total);

            fragment.setArguments(bundle);

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.navHostFragment, fragment)
                    .addToBackStack(null)
                    .commit();
        });



    }

    @SuppressLint("DefaultLocale")
    private void updateTotalPrice() {
        total = 0;

        for (Snack s : snacks) {
            total += s.getQuantity() * s.getPrice();
        }

        totalPrice.setText(String.format("Total Price: $%.2f", total));
    }



    private void init(View view){

        totalPrice=view.findViewById(R.id.totalPrice);
        btnConfirm= view.findViewById(R.id.btnConfirm);
        btnReset= view.findViewById(R.id.btnReset);
        lvSnacks= view.findViewById(R.id.lvSnacks);

        snacks= new ArrayList<>();

        DatabaseManager dbManager = new DatabaseManager(requireContext());
        dbManager.open();

        snacks = dbManager.getAllSnacks();

        dbManager.close();


        snackAdapter= new SnackAdapter(requireContext(), snacks);

        lvSnacks.setAdapter(snackAdapter);

        updateTotalPrice();


    }


}