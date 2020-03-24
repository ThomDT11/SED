package com.example.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.game.planet15_game_view.screenRatioX;
import static com.example.game.planet15_game_view.screenRatioY;

public class planet15_enemy {

    public int speed = 20;
    public boolean wasShot = true;
    int x, y, width, height, planet15EnemyCounter = 1;
    Bitmap planet15_enemy1, planet15_enemy2, planet15_enemy3, planet15_enemy4;

    planet15_enemy (Resources res){

        planet15_enemy1 = BitmapFactory.decodeResource(res, R.drawable.b2_f1);
        planet15_enemy2 = BitmapFactory.decodeResource(res, R.drawable.b2_f2);
        planet15_enemy3 = BitmapFactory.decodeResource(res, R.drawable.b2_f3);
        planet15_enemy4 = BitmapFactory.decodeResource(res, R.drawable.b2_f3);

        width = planet15_enemy1.getWidth();
        height = planet15_enemy1.getHeight();

        width /= 3;
        height /= 3;

        width *= (int) screenRatioX;
        height *= (int) screenRatioY;

        planet15_enemy1 = Bitmap.createScaledBitmap(planet15_enemy1, width, height, false);
        planet15_enemy2 = Bitmap.createScaledBitmap(planet15_enemy2, width, height, false);
        planet15_enemy3 = Bitmap.createScaledBitmap(planet15_enemy3, width, height, false);
        planet15_enemy4 = Bitmap.createScaledBitmap(planet15_enemy4, width, height, false);

        y = -height;

    }


    Bitmap getplanet15_enemy(){

        if ( planet15EnemyCounter == 1){
            planet15EnemyCounter++;
            return planet15_enemy1;
        }

        if ( planet15EnemyCounter == 2){
            planet15EnemyCounter++;
            return planet15_enemy2;
        }

        if ( planet15EnemyCounter == 3){
            planet15EnemyCounter++;
            return planet15_enemy3;
        }

        planet15EnemyCounter = 1 ;

        return planet15_enemy4;

    }

    Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }
}
