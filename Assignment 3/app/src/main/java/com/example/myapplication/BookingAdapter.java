package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.*;
import android.widget.*;
import androidx.annotation.*;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {

    Context context;
    ArrayList<Booking> list;
    OnCancelClick listener;

    public interface OnCancelClick {
        void onCancelClick(Booking booking);
    }

    public BookingAdapter(Context context, ArrayList<Booking> list, OnCancelClick listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.booking_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int pos) {

        Booking b = list.get(pos);

        h.movieName.setText(b.movieName);
        h.seats.setText("Tickets: " + b.seats);
        h.total.setText(String.format("Total: %.2f USD", b.totalPrice));

        h.poster.setImageResource(b.poster);

        h.btnCancel.setOnClickListener(v -> listener.onCancelClick(b));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView poster, btnCancel;
        TextView movieName, seats, total;


        @SuppressLint("WrongViewCast")
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            poster = itemView.findViewById(R.id.poster);
            movieName = itemView.findViewById(R.id.movieName);
            seats = itemView.findViewById(R.id.seats);
            total = itemView.findViewById(R.id.totalPrice);
            btnCancel = itemView.findViewById(R.id.btnCancel);
        }
    }
}