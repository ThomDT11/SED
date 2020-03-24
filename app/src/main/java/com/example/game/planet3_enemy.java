package com.example.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.game.planet3_game_view.screenRatioX;
import static com.example.game.planet3_game_view.screenRatioY;

public class planet3_enemy {

    public int speed = 20;
    public boolean wasShot = true;
    int x, y, width, height, planet3EnemyCounter = 1;
    Bitmap planet3_enemy1, planet3_enemy2, planet3_enemy3, planet3_enemy4;

    planet3_enemy (Resources res){

        planet3_enemy1 = BitmapFactory.decodeResource(res, R.drawable.e1);
        planet3_enemy2 = BitmapFactory.decodeResource(res, R.drawable.e1);
        planet3_enemy3 = BitmapFactory.decodeResource(res, R.drawable.e1);
        planet3_enemy4 = BitmapFactory.decodeResource(res, R.drawable.e1);

        width = planet3_enemy1.getWidth();
        height = planet3_enemy1.getHeight();

        width /= 0.8;
        height /= 0.8;

        width *= (int) screenRatioX;
        height *= (int) screenRatioY;

        planet3_enemy1 = Bitmap.createScaledBitmap(planet3_enemy1, width, height, false);
        planet3_enemy2 = Bitmap.createScaledBitmap(planet3_enemy2, width, height, false);
        planet3_enemy3 = Bitmap.createScaledBitmap(planet3_enemy3, width, height, false);
        planet3_enemy4 = Bitmap.createScaledBitmap(planet3_enemy4, width, height, false);

        y = -height;

    }


    Bitmap getplanet3_enemy(){

        if ( planet3EnemyCounter == 1){
            planet3EnemyCounter++;
            return planet3_enemy1;
        }

        if ( planet3EnemyCounter == 2){
            planet3EnemyCounter++;
            return planet3_enemy2;
        }

        if ( planet3EnemyCounter == 3){
            planet3EnemyCounter++;
            return planet3_enemy3;
        }

        planet3EnemyCounter = 1 ;

        return planet3_enemy4;

    }

    Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }
}
