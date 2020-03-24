package com.example.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.game.planet7_game_view.screenRatioX;
import static com.example.game.planet7_game_view.screenRatioY;

public class planet7_enemy {

    public int speed = 20;
    public boolean wasShot = true;
    int x, y, width, height, planet7EnemyCounter = 1;
    Bitmap planet7_enemy1, planet7_enemy2, planet7_enemy3, planet7_enemy4;

    planet7_enemy (Resources res){

        planet7_enemy1 = BitmapFactory.decodeResource(res, R.drawable.e2_f1);
        planet7_enemy2 = BitmapFactory.decodeResource(res, R.drawable.e2_f2);
        planet7_enemy3 = BitmapFactory.decodeResource(res, R.drawable.e2_f3);
        planet7_enemy4 = BitmapFactory.decodeResource(res, R.drawable.e2_f4);

        width = planet7_enemy1.getWidth();
        height = planet7_enemy1.getHeight();

        width /= 0.8;
        height /= 0.8;

        width *= (int) screenRatioX;
        height *= (int) screenRatioY;

        planet7_enemy1 = Bitmap.createScaledBitmap(planet7_enemy1, width, height, false);
        planet7_enemy2 = Bitmap.createScaledBitmap(planet7_enemy2, width, height, false);
        planet7_enemy3 = Bitmap.createScaledBitmap(planet7_enemy3, width, height, false);
        planet7_enemy4 = Bitmap.createScaledBitmap(planet7_enemy4, width, height, false);

        y = -height;

    }


    Bitmap getplanet7_enemy(){

        if ( planet7EnemyCounter == 1){
            planet7EnemyCounter++;
            return planet7_enemy1;
        }

        if ( planet7EnemyCounter == 2){
            planet7EnemyCounter++;
            return planet7_enemy2;
        }

        if ( planet7EnemyCounter == 3){
            planet7EnemyCounter++;
            return planet7_enemy3;
        }

        planet7EnemyCounter = 1 ;

        return planet7_enemy4;

    }

    Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }
}
