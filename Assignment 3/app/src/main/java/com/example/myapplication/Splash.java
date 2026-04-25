package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Splash extends AppCompatActivity {

    ImageView logo,name;
    Animation fade_transition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.splash), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();

        setAnimations();

        movetoOnboarding();

    }

    private void movetoOnboarding()
    {
        new Handler().postDelayed(()->{
            startActivity(new Intent(Splash.this, Onboarding.class));
        },5000);

    }


    void setAnimations()
    {
        logo.setAnimation(fade_transition);
        name.setAnimation(fade_transition);
    }

    private void init()
    {
        logo=findViewById(R.id.logo);
        name=findViewById(R.id.name);
        fade_transition= AnimationUtils.loadAnimation(this, R.anim.fade_transition);
    }
}


