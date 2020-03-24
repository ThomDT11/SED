package com.example.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.game.planet9_game_view.screenRatioX;
import static com.example.game.planet9_game_view.screenRatioY;

public class planet9_enemy {

    public int speed = 10;
    public boolean wasShot = true;
    int x, y, width, height, planet9EnemyCounter = 1;
    Bitmap planet9_enemy1, planet9_enemy2, planet9_enemy3, planet9_enemy4;

    planet9_enemy (Resources res){

        planet9_enemy1 = BitmapFactory.decodeResource(res, R.drawable.b1_f1);
        planet9_enemy2 = BitmapFactory.decodeResource(res, R.drawable.b1_f2);
        planet9_enemy3 = BitmapFactory.decodeResource(res, R.drawable.b1_f1);
        planet9_enemy4 = BitmapFactory.decodeResource(res, R.drawable.b1_f2);

        width = planet9_enemy1.getWidth();
        height = planet9_enemy1.getHeight();

        width /= 2.5;
        height /= 2.5;

        width *= (int) screenRatioX;
        height *= (int) screenRatioY;

        planet9_enemy1 = Bitmap.createScaledBitmap(planet9_enemy1, width, height, false);
        planet9_enemy2 = Bitmap.createScaledBitmap(planet9_enemy2, width, height, false);
        planet9_enemy3 = Bitmap.createScaledBitmap(planet9_enemy3, width, height, false);
        planet9_enemy4 = Bitmap.createScaledBitmap(planet9_enemy4, width, height, false);

        y = -height;

    }


    Bitmap getplanet9_enemy(){

        if ( planet9EnemyCounter == 1){
            planet9EnemyCounter++;
            return planet9_enemy1;
        }

        if ( planet9EnemyCounter == 2){
            planet9EnemyCounter++;
            return planet9_enemy2;
        }

        if ( planet9EnemyCounter == 3){
            planet9EnemyCounter++;
            return planet9_enemy3;
        }

        planet9EnemyCounter = 1 ;

        return planet9_enemy4;

    }

    Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }
}
