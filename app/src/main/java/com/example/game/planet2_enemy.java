package com.example.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.game.planet2_game_view.screenRatioX;
import static com.example.game.planet2_game_view.screenRatioY;

public class planet2_enemy {

    public int speed = 15;
    public boolean wasShot = true;
    int x, y, width, height, planet2EnemyCounter = 1;
    Bitmap planet2_enemy1, planet2_enemy2, planet2_enemy3, planet2_enemy4;

    planet2_enemy (Resources res){

        planet2_enemy1 = BitmapFactory.decodeResource(res, R.drawable.e1);
        planet2_enemy2 = BitmapFactory.decodeResource(res, R.drawable.e1);
        planet2_enemy3 = BitmapFactory.decodeResource(res, R.drawable.e1);
        planet2_enemy4 = BitmapFactory.decodeResource(res, R.drawable.e1);

        width = planet2_enemy1.getWidth();
        height = planet2_enemy1.getHeight();

        width /= 0.8;
        height /= 0.8;

        width *= (int) screenRatioX;
        height *= (int) screenRatioY;

        planet2_enemy1 = Bitmap.createScaledBitmap(planet2_enemy1, width, height, false);
        planet2_enemy2 = Bitmap.createScaledBitmap(planet2_enemy2, width, height, false);
        planet2_enemy3 = Bitmap.createScaledBitmap(planet2_enemy3, width, height, false);
        planet2_enemy4 = Bitmap.createScaledBitmap(planet2_enemy4, width, height, false);

        y = -height;

    }


    Bitmap getplanet2_enemy(){

        if ( planet2EnemyCounter == 1){
            planet2EnemyCounter++;
            return planet2_enemy1;
        }

        if ( planet2EnemyCounter == 2){
            planet2EnemyCounter++;
            return planet2_enemy2;
        }

        if ( planet2EnemyCounter == 3){
            planet2EnemyCounter++;
            return planet2_enemy3;
        }

        planet2EnemyCounter = 1 ;

        return planet2_enemy4;

    }

    Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }
}
