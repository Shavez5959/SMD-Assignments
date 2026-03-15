package com.example.assignment1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class snacks extends AppCompatActivity {

    Button btnPopcornMinus, btnPopcornPlus, btnNachosMinus, btnNachosPlus, btnDrinkMinus, btnDrinkPlus, btnCandyMinus, btnCandyPlus, btnReset, btnConfirm;
    TextView tvPopcornQty, tvNachosQty, tvDrinkQty, tvCandyQty, totalPrice;

    double pricePopcorn = 8.99;
    double priceNachos = 7.99;
    double priceDrink = 5.99;
    double priceCandy = 6.99;

    double total=0;

    String name;
    int no_tickets;
    int tickets_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.snacks_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.snacks), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();

        btnPopcornPlus.setOnClickListener(v -> updateQuantity(tvPopcornQty, 1));
        btnPopcornMinus.setOnClickListener(v -> updateQuantity(tvPopcornQty, -1));


        btnNachosPlus.setOnClickListener(v -> updateQuantity(tvNachosQty, 1));
        btnNachosMinus.setOnClickListener(v -> updateQuantity(tvNachosQty, -1));


        btnDrinkPlus.setOnClickListener(v -> updateQuantity(tvDrinkQty, 1));
        btnDrinkMinus.setOnClickListener(v -> updateQuantity(tvDrinkQty, -1));


        btnCandyPlus.setOnClickListener(v -> updateQuantity(tvCandyQty, 1));
        btnCandyMinus.setOnClickListener(v -> updateQuantity(tvCandyQty, -1));


        Intent i = getIntent();
        name=i.getStringExtra("movie-key");
        no_tickets=i.getIntExtra("tickets-key", 0);
        tickets_price=i.getIntExtra("total-key", 0);

        btnReset.setOnClickListener(v-> {
            tvPopcornQty.setText("0");
            tvNachosQty.setText("0");
            tvDrinkQty.setText("0");
            tvCandyQty.setText("0");

            updateTotalPrice();
        });

        btnConfirm.setOnClickListener(v->{
            Intent intent = new Intent(snacks.this, ticket_summary.class);
            intent.putExtra("movie-key", name);
            intent.putExtra("tickets-key", no_tickets);
            intent.putExtra("tickets-total-key", tickets_price);
            intent.putExtra("snacks-total-key", total);
            startActivity(intent);
        });



    }

    private void updateQuantity(TextView tv, int count)
    {
        int quantity=Integer.parseInt(tv.getText().toString().trim());
        quantity+=count;
        if (quantity<0)
        {
            quantity=0;
        }

        tv.setText(String.valueOf(quantity));

        updateTotalPrice();

    }

    @SuppressLint("DefaultLocale")
    private void updateTotalPrice() {
        int qtyPopcorn = Integer.parseInt(tvPopcornQty.getText().toString());
        int qtyNachos = Integer.parseInt(tvNachosQty.getText().toString());
        int qtyDrink = Integer.parseInt(tvDrinkQty.getText().toString());
        int qtyCandy = Integer.parseInt(tvCandyQty.getText().toString());

        total = qtyPopcorn * pricePopcorn + qtyNachos * priceNachos + qtyDrink * priceDrink + qtyCandy * priceCandy;

        totalPrice.setText(String.format("Total Price: $%.2f", total));
    }


    private void init()
    {
        btnPopcornMinus = findViewById(R.id.btnPopcornMinus);
        btnPopcornPlus = findViewById(R.id.btnPopcornPlus);
        tvPopcornQty = findViewById(R.id.tvPopcornQty);

        btnNachosMinus = findViewById(R.id.btnNachosMinus);
        btnNachosPlus = findViewById(R.id.btnNachosPlus);
        tvNachosQty = findViewById(R.id.tvNachosQty);

        btnDrinkMinus = findViewById(R.id.btnDrinkMinus);
        btnDrinkPlus = findViewById(R.id.btnDrinkPlus);
        tvDrinkQty = findViewById(R.id.tvDrinkQty);

        btnCandyMinus = findViewById(R.id.btnCandyMinus);
        btnCandyPlus = findViewById(R.id.btnCandyPlus);
        tvCandyQty = findViewById(R.id.tvCandyQty);

        totalPrice = findViewById(R.id.totalPrice);

        btnReset= findViewById(R.id.btnReset);
        btnConfirm = findViewById(R.id.btnConfirm);

    }

}
