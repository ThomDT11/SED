package com.example.game;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PlanetScreen extends AppCompatActivity {

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planet_screen);

        findViewById(R.id.arrow_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PlanetScreen.this, PlanetScreenTwo.class));
            }
        });


        findViewById(R.id.arrow_back2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PlanetScreen.this, MainActivity.class));
            }
        });

        findViewById(R.id.image_planet1_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PlanetScreen.this, earth_game.class));
            }
        });

        findViewById(R.id.image_planet2_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PlanetScreen.this, planet2_game.class));
            }
        });

        findViewById(R.id.image_planet3_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PlanetScreen.this, planet3_game.class));
            }
        });

        findViewById(R.id.image_planet4_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PlanetScreen.this, planet4_game.class));
            }
        });

    }
}
