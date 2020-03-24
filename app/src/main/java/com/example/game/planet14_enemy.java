package com.example.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.game.planet14_game_view.screenRatioX;
import static com.example.game.planet14_game_view.screenRatioY;

public class planet14_enemy {

    public int speed = 15;
    public boolean wasShot = true;
    int x, y, width, height, planet14EnemyCounter = 1;
    Bitmap planet14_enemy1, planet14_enemy2, planet14_enemy3, planet14_enemy4;

    planet14_enemy (Resources res){

        planet14_enemy1 = BitmapFactory.decodeResource(res, R.drawable.b2_f1);
        planet14_enemy2 = BitmapFactory.decodeResource(res, R.drawable.b2_f2);
        planet14_enemy3 = BitmapFactory.decodeResource(res, R.drawable.b2_f3);
        planet14_enemy4 = BitmapFactory.decodeResource(res, R.drawable.b2_f3);

        width = planet14_enemy1.getWidth();
        height = planet14_enemy1.getHeight();

        width /= 3;
        height /= 3;

        width *= (int) screenRatioX;
        height *= (int) screenRatioY;

        planet14_enemy1 = Bitmap.createScaledBitmap(planet14_enemy1, width, height, false);
        planet14_enemy2 = Bitmap.createScaledBitmap(planet14_enemy2, width, height, false);
        planet14_enemy3 = Bitmap.createScaledBitmap(planet14_enemy3, width, height, false);
        planet14_enemy4 = Bitmap.createScaledBitmap(planet14_enemy4, width, height, false);

        y = -height;

    }


    Bitmap getplanet14_enemy(){

        if ( planet14EnemyCounter == 1){
            planet14EnemyCounter++;
            return planet14_enemy1;
        }

        if ( planet14EnemyCounter == 2){
            planet14EnemyCounter++;
            return planet14_enemy2;
        }

        if ( planet14EnemyCounter == 3){
            planet14EnemyCounter++;
            return planet14_enemy3;
        }

        planet14EnemyCounter = 1 ;

        return planet14_enemy4;

    }

    Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }
}
