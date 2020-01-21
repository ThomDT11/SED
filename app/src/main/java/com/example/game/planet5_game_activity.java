package com.example.game;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.WindowManager;

public class planet5_game_activity extends AppCompatActivity {

    private planet5_game_view Planet5_game_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);

        Planet5_game_view = new planet5_game_view(this, point.x, point.y);


        setContentView(Planet5_game_view);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Planet5_game_view.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Planet5_game_view.resume();
    }
}
