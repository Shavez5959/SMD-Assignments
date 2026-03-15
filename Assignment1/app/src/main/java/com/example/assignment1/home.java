package com.example.assignment1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class home extends AppCompatActivity {

    TextView the_dark_knight, inception, interstellar;

    Button btnBook1, btnBook2, btnBook3, btnTrailer1, btnTrailer2, btnTrailer3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.home_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.home), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();

        String movie1=the_dark_knight.getText().toString().trim();
        String movie2=inception.getText().toString().trim();
        String movie3=interstellar.getText().toString().trim();

        btnBook1.setOnClickListener(v->{
            Intent i= new Intent(home.this,seat_selection.class);
            i.putExtra("movie1-key", movie1);
            startActivity(i);
        });

        btnBook2.setOnClickListener(v->{
            Intent i= new Intent(home.this,seat_selection.class);
            i.putExtra("movie2-key", movie2);
            startActivity(i);
        });

        btnBook3.setOnClickListener(v->{
            Intent i= new Intent(home.this,seat_selection.class);
            i.putExtra("movie3-key", movie3);
            startActivity(i);

        });


        btnTrailer1.setOnClickListener(v -> {
            String url = "https://www.youtube.com/watch?v=EXeTwQWrcwY";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });


        btnTrailer2.setOnClickListener(v -> {
            String url = "https://www.youtube.com/watch?v=YoHD9XEInc0";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });


        btnTrailer3.setOnClickListener(v -> {
            String url = "https://www.youtube.com/watch?v=zSWdZVtXT7E";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });


    }

    private void init()
    {
        btnBook1=findViewById(R.id.btnBook1);
        btnBook2=findViewById(R.id.btnBook2);
        btnBook3=findViewById(R.id.btnBook3);

        btnTrailer1=findViewById(R.id.btnTrailer1);
        btnTrailer2=findViewById(R.id.btnTrailer2);
        btnTrailer3=findViewById(R.id.btnTrailer3);

        the_dark_knight=findViewById(R.id.the_dark_knight);
        inception=findViewById(R.id.inception);
        interstellar=findViewById(R.id.interstellar);

    }
}
