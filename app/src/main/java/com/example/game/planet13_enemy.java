package com.example.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.game.planet13_game_view.screenRatioX;
import static com.example.game.planet13_game_view.screenRatioY;

public class planet13_enemy {

    public int speed = 10;
    public boolean wasShot = true;
    int x, y, width, height, planet13EnemyCounter = 1;
    Bitmap planet13_enemy1, planet13_enemy2, planet13_enemy3, planet13_enemy4;

    planet13_enemy (Resources res){

        planet13_enemy1 = BitmapFactory.decodeResource(res, R.drawable.b2_f1);
        planet13_enemy2 = BitmapFactory.decodeResource(res, R.drawable.b2_f2);
        planet13_enemy3 = BitmapFactory.decodeResource(res, R.drawable.b2_f3);
        planet13_enemy4 = BitmapFactory.decodeResource(res, R.drawable.b2_f3);

        width = planet13_enemy1.getWidth();
        height = planet13_enemy1.getHeight();

        width /= 3;
        height /= 3;

        width *= (int) screenRatioX;
        height *= (int) screenRatioY;

        planet13_enemy1 = Bitmap.createScaledBitmap(planet13_enemy1, width, height, false);
        planet13_enemy2 = Bitmap.createScaledBitmap(planet13_enemy2, width, height, false);
        planet13_enemy3 = Bitmap.createScaledBitmap(planet13_enemy3, width, height, false);
        planet13_enemy4 = Bitmap.createScaledBitmap(planet13_enemy4, width, height, false);

        y = -height;

    }


    Bitmap getplanet13_enemy(){

        if ( planet13EnemyCounter == 1){
            planet13EnemyCounter++;
            return planet13_enemy1;
        }

        if ( planet13EnemyCounter == 2){
            planet13EnemyCounter++;
            return planet13_enemy2;
        }

        if ( planet13EnemyCounter == 3){
            planet13EnemyCounter++;
            return planet13_enemy3;
        }

        planet13EnemyCounter = 1 ;

        return planet13_enemy4;

    }

    Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }
}
