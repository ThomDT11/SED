package com.example.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.game.planet10_game_view.screenRatioX;
import static com.example.game.planet10_game_view.screenRatioY;

public class planet10_enemy {

    public int speed = 15;
    public boolean wasShot = true;
    int x, y, width, height, planet10EnemyCounter = 1;
    Bitmap planet10_enemy1, planet10_enemy2, planet10_enemy3, planet10_enemy4;

    planet10_enemy (Resources res){

        planet10_enemy1 = BitmapFactory.decodeResource(res, R.drawable.b1_f1);
        planet10_enemy2 = BitmapFactory.decodeResource(res, R.drawable.b1_f2);
        planet10_enemy3 = BitmapFactory.decodeResource(res, R.drawable.b1_f1);
        planet10_enemy4 = BitmapFactory.decodeResource(res, R.drawable.b1_f2);

        width = planet10_enemy1.getWidth();
        height = planet10_enemy1.getHeight();

        width /= 2.5;
        height /= 2.5;

        width *= (int) screenRatioX;
        height *= (int) screenRatioY;

        planet10_enemy1 = Bitmap.createScaledBitmap(planet10_enemy1, width, height, false);
        planet10_enemy2 = Bitmap.createScaledBitmap(planet10_enemy2, width, height, false);
        planet10_enemy3 = Bitmap.createScaledBitmap(planet10_enemy3, width, height, false);
        planet10_enemy4 = Bitmap.createScaledBitmap(planet10_enemy4, width, height, false);

        y = -height;

    }


    Bitmap getplanet10_enemy(){

        if ( planet10EnemyCounter == 1){
            planet10EnemyCounter++;
            return planet10_enemy1;
        }

        if ( planet10EnemyCounter == 2){
            planet10EnemyCounter++;
            return planet10_enemy2;
        }

        if ( planet10EnemyCounter == 3){
            planet10EnemyCounter++;
            return planet10_enemy3;
        }

        planet10EnemyCounter = 1 ;

        return planet10_enemy4;

    }

    Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }
}
