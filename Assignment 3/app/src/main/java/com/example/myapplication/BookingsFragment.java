package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.*;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.*;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.*;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

import java.util.ArrayList;

public class BookingsFragment extends Fragment {

    RecyclerView recyclerView;
    EditText searchBar;

    ArrayList<Booking> list;
    ArrayList<Booking> filteredList;

    BookingAdapter adapter;

    DatabaseReference ref;
    String userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bookings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        recyclerView = view.findViewById(R.id.recyclerViewBookings);
        searchBar = view.findViewById(R.id.searchBar);

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        list = new ArrayList<>();
        filteredList = new ArrayList<>();

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Toast.makeText(requireContext(), "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        ref = FirebaseDatabase.getInstance(
                "https://my-application-99baa-default-rtdb.asia-southeast1.firebasedatabase.app/"
        ).getReference("bookings").child(userId);

        adapter = new BookingAdapter(requireContext(), filteredList, this::showCancelDialog);
        recyclerView.setAdapter(adapter);

        setupSearch();
        loadBookings();
    }


    private void setupSearch() {

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterList(s.toString());
            }

            @Override public void afterTextChanged(Editable s) {}
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void filterList(String query) {

        filteredList.clear();

        if (query.isEmpty()) {
            filteredList.addAll(list);
        } else {
            for (Booking b : list) {
                if (b.movieName.toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(b);
                }
            }
        }

        adapter.notifyDataSetChanged();
    }


    private void loadBookings() {

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.clear();

                for (DataSnapshot data : snapshot.getChildren()) {

                    Booking booking = data.getValue(Booking.class);

                    if (booking != null) {
                        booking.id = data.getKey();
                        list.add(booking);
                    }
                }


                filterList(searchBar.getText().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void showCancelDialog(Booking booking) {

        new AlertDialog.Builder(requireContext())
                .setTitle("Cancel Booking")
                .setMessage("Are you sure you want to cancel this booking?")
                .setPositiveButton("Yes", (d, w) -> {

                    ref.child(booking.id).removeValue()
                            .addOnSuccessListener(unused ->
                                    Toast.makeText(requireContext(),
                                            "Booking Cancelled Successfully",
                                            Toast.LENGTH_SHORT).show()
                            )
                            .addOnFailureListener(e ->
                                    Toast.makeText(requireContext(),
                                            "Error: " + e.getMessage(),
                                            Toast.LENGTH_SHORT).show()
                            );

                })
                .setNegativeButton("No", null)
                .show();
    }
}