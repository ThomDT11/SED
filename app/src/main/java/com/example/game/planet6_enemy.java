package com.example.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.game.planet6_game_view.screenRatioX;
import static com.example.game.planet6_game_view.screenRatioY;

public class planet6_enemy {

    public int speed = 15;
    public boolean wasShot = true;
    int x, y, width, height, planet6EnemyCounter = 1;
    Bitmap planet6_enemy1, planet6_enemy2, planet6_enemy3, planet6_enemy4;

    planet6_enemy (Resources res){

        planet6_enemy1 = BitmapFactory.decodeResource(res, R.drawable.e2_f1);
        planet6_enemy2 = BitmapFactory.decodeResource(res, R.drawable.e2_f2);
        planet6_enemy3 = BitmapFactory.decodeResource(res, R.drawable.e2_f3);
        planet6_enemy4 = BitmapFactory.decodeResource(res, R.drawable.e2_f4);

        width = planet6_enemy1.getWidth();
        height = planet6_enemy1.getHeight();

        width /= 0.8;
        height /= 0.8;

        width *= (int) screenRatioX;
        height *= (int) screenRatioY;

        planet6_enemy1 = Bitmap.createScaledBitmap(planet6_enemy1, width, height, false);
        planet6_enemy2 = Bitmap.createScaledBitmap(planet6_enemy2, width, height, false);
        planet6_enemy3 = Bitmap.createScaledBitmap(planet6_enemy3, width, height, false);
        planet6_enemy4 = Bitmap.createScaledBitmap(planet6_enemy4, width, height, false);

        y = -height;

    }


    Bitmap getplanet6_enemy(){

        if ( planet6EnemyCounter == 1){
            planet6EnemyCounter++;
            return planet6_enemy1;
        }

        if ( planet6EnemyCounter == 2){
            planet6EnemyCounter++;
            return planet6_enemy2;
        }

        if ( planet6EnemyCounter == 3){
            planet6EnemyCounter++;
            return planet6_enemy3;
        }

        planet6EnemyCounter = 1 ;

        return planet6_enemy4;

    }

    Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }
}
