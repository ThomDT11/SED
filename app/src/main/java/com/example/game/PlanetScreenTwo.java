package com.example.game;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PlanetScreenTwo extends AppCompatActivity {

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planet_screen_two);

        findViewById(R.id.arrow_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PlanetScreenTwo.this, PlanetScreen.class));
            }
        });

        findViewById(R.id.image_planet9_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PlanetScreenTwo.this, planet9_game.class));
            }
        });

        findViewById(R.id.image_planet10_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PlanetScreenTwo.this, planet10_game.class));
            }
        });

        findViewById(R.id.image_planet11_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PlanetScreenTwo.this, planet11_game.class));
            }
        });

        findViewById(R.id.image_planet12_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PlanetScreenTwo.this, planet12_game.class));
            }
        });

        findViewById(R.id.image_planet13_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PlanetScreenTwo.this, planet13_game.class));
            }
        });

        findViewById(R.id.image_planet14_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PlanetScreenTwo.this, planet14_game.class));
            }
        });

        findViewById(R.id.image_planet15_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PlanetScreenTwo.this, planet15_game.class));
            }
        });

        findViewById(R.id.image_planet16_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PlanetScreenTwo.this, planet16_game.class));
            }
        });



    }
}
