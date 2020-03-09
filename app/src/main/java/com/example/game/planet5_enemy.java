package com.example.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.game.planet5_game_view.screenRatioX;
import static com.example.game.planet5_game_view.screenRatioY;

public class planet5_enemy {

    public int speed = 10;
    public boolean wasShot = true;
    int x, y, width, height, planet5EnemyCounter = 1;
    Bitmap planet5_enemy1, planet5_enemy2, planet5_enemy3, planet5_enemy4;

    planet5_enemy (Resources res){

        planet5_enemy1 = BitmapFactory.decodeResource(res, R.drawable.e2_f1);
        planet5_enemy2 = BitmapFactory.decodeResource(res, R.drawable.e2_f2);
        planet5_enemy3 = BitmapFactory.decodeResource(res, R.drawable.e2_f3);
        planet5_enemy4 = BitmapFactory.decodeResource(res, R.drawable.e2_f4);

        width = planet5_enemy1.getWidth();
        height = planet5_enemy1.getHeight();

        width /= 0.8;
        height /= 0.8;

        width *= (int) screenRatioX;
        height *= (int) screenRatioY;

        planet5_enemy1 = Bitmap.createScaledBitmap(planet5_enemy1, width, height, false);
        planet5_enemy2 = Bitmap.createScaledBitmap(planet5_enemy2, width, height, false);
        planet5_enemy3 = Bitmap.createScaledBitmap(planet5_enemy3, width, height, false);
        planet5_enemy4 = Bitmap.createScaledBitmap(planet5_enemy4, width, height, false);

        y = -height;

    }


    Bitmap getplanet5_enemy(){

        if ( planet5EnemyCounter == 1){
            planet5EnemyCounter++;
            return planet5_enemy1;
        }

        if ( planet5EnemyCounter == 2){
            planet5EnemyCounter++;
            return planet5_enemy2;
        }

        if ( planet5EnemyCounter == 3){
            planet5EnemyCounter++;
            return planet5_enemy3;
        }

        planet5EnemyCounter = 1 ;

        return planet5_enemy4;

    }

    Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }
}
