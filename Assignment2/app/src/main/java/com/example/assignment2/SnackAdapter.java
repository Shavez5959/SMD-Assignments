package com.example.assignment2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SnackAdapter extends ArrayAdapter<Snack> {

    Context context;

    Button btnMinus, btnPlus;

    TextView tvQty, SnackName, SnackDescription, SnackPrice;

    ImageView SnackImage;

    OnQuantityChangedListener listener;


    public interface OnQuantityChangedListener{
        void onQuantityChanged();
    }

    public void setOnQuantityChangeListener(OnQuantityChangedListener listener) {
        this.listener = listener;
    }


    public SnackAdapter(@NonNull Context context, @NonNull List<Snack> objects) {
        super(context,0, objects);

        this.context= context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.snack_item, parent, false);
        }

        Snack snack= getItem(position);

        init(convertView);

        SnackImage.setImageResource(snack.getImage());

        SnackName.setText(snack.getName());

        SnackDescription.setText(snack.getDescription());

        SnackPrice.setText(String.format("$%.2f", snack.getPrice()));

        tvQty.setText(String.valueOf(snack.getQuantity()));

        btnPlus.setOnClickListener(v -> updateQuantity(snack, 1, tvQty));
        btnMinus.setOnClickListener(v -> updateQuantity(snack, -1, tvQty));


        return convertView;

    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    private void updateQuantity(Snack snack, int count, TextView tv)
    {
        int quantity= snack.getQuantity() + count;
        if (quantity<0)
        {
            quantity=0;
        }

        snack.setQuantity(quantity);

        notifyDataSetChanged();

        if (listener!=null)
        {
            listener.onQuantityChanged();
        }

    }


    private void init(View convertView)
    {
        SnackImage= convertView.findViewById(R.id.SnackImage);
        SnackName= convertView.findViewById(R.id.SnackName);
        SnackDescription= convertView.findViewById(R.id.SnackDescription);
        SnackPrice= convertView.findViewById(R.id.SnackPrice);

        btnMinus = convertView.findViewById(R.id.btnMinus);
        btnPlus = convertView.findViewById(R.id.btnPlus);
        tvQty = convertView.findViewById(R.id.tvQty);

    }
}
