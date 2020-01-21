package com.example.game;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.WindowManager;

public class planet16_game_activity extends AppCompatActivity {

    private planet16_game_view Planet16_game_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);

        Planet16_game_view = new planet16_game_view(this, point.x, point.y);


        setContentView(Planet16_game_view);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Planet16_game_view.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Planet16_game_view.resume();
    }
}
