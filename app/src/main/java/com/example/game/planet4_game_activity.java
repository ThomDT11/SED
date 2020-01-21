package com.example.game;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.WindowManager;

public class planet4_game_activity extends AppCompatActivity {

    private planet4_game_view Planet4_game_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);

        Planet4_game_view = new planet4_game_view(this, point.x, point.y);


        setContentView(Planet4_game_view);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Planet4_game_view.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Planet4_game_view.resume();
    }
}
