package com.example.game;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class planet2_game extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planet2_game);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        findViewById(R.id.planet_2_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(planet2_game.this, planet2_game_activity.class));
            }
        });


        TextView highScoreText2 = findViewById(R.id.highScoreText2);

        SharedPreferences prefs = getSharedPreferences("game" , MODE_PRIVATE);
        highScoreText2.setText("High Score:  " + prefs.getInt("highscore", 0   ));
    }
}
