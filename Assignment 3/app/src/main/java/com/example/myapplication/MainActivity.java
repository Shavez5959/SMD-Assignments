package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        setupNavHeader();


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.navHostFragment, new HomeFragment())
                    .commit();

            navigationView.setCheckedItem(R.id.homeFragment);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.homeFragment) {

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.navHostFragment, new HomeFragment())
                    .commit();

        } else if (id == R.id.myBookingsFragment) {

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.navHostFragment, new BookingsFragment())
                    .commit();

        } else if (id == R.id.logout) {

            logoutUser();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setupNavHeader() {


        View headerView = navigationView.getHeaderView(0);

        if (headerView == null) {
            headerView = navigationView.inflateHeaderView(R.layout.nav_header);
        }

        TextView textViewName = headerView.findViewById(R.id.textViewName);
        TextView textViewEmail = headerView.findViewById(R.id.textViewEmail);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null) {
            textViewName.setText("Guest");
            textViewEmail.setText("");
            return;
        }




        FirebaseDatabase.getInstance("https://my-application-99baa-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Users")
                .child(user.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (!snapshot.exists()) return;


                        String name = snapshot.child("name").getValue(String.class);
                        String email = snapshot.child("email").getValue(String.class);

                        textViewName.setText(name);
                        textViewEmail.setText(email);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void logoutUser() {

        FirebaseAuth.getInstance().signOut();

        getSharedPreferences("session-key", MODE_PRIVATE)
                .edit()
                .clear()
                .apply();

        Intent intent = new Intent(MainActivity.this, Login.class);
        startActivity(intent);
        finish();
    }


}