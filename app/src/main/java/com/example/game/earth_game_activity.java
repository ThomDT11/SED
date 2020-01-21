package com.example.game;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.WindowManager;

public class earth_game_activity extends AppCompatActivity {

    private earth_game_view Earth_game_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);

        Earth_game_view = new earth_game_view(this, point.x, point.y);


        setContentView(Earth_game_view);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Earth_game_view.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Earth_game_view.resume();
    }
}
