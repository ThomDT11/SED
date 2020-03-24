package com.example.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.game.planet11_game_view.screenRatioX;
import static com.example.game.planet11_game_view.screenRatioY;

public class planet11_enemy {

    public int speed = 20;
    public boolean wasShot = true;
    int x, y, width, height, planet11EnemyCounter = 1;
    Bitmap planet11_enemy1, planet11_enemy2, planet11_enemy3, planet11_enemy4;

    planet11_enemy (Resources res){

        planet11_enemy1 = BitmapFactory.decodeResource(res, R.drawable.b1_f1);
        planet11_enemy2 = BitmapFactory.decodeResource(res, R.drawable.b1_f2);
        planet11_enemy3 = BitmapFactory.decodeResource(res, R.drawable.b1_f1);
        planet11_enemy4 = BitmapFactory.decodeResource(res, R.drawable.b1_f2);

        width = planet11_enemy1.getWidth();
        height = planet11_enemy1.getHeight();

        width /= 2.5;
        height /= 2.5;

        width *= (int) screenRatioX;
        height *= (int) screenRatioY;

        planet11_enemy1 = Bitmap.createScaledBitmap(planet11_enemy1, width, height, false);
        planet11_enemy2 = Bitmap.createScaledBitmap(planet11_enemy2, width, height, false);
        planet11_enemy3 = Bitmap.createScaledBitmap(planet11_enemy3, width, height, false);
        planet11_enemy4 = Bitmap.createScaledBitmap(planet11_enemy4, width, height, false);

        y = -height;

    }


    Bitmap getplanet11_enemy(){

        if ( planet11EnemyCounter == 1){
            planet11EnemyCounter++;
            return planet11_enemy1;
        }

        if ( planet11EnemyCounter == 2){
            planet11EnemyCounter++;
            return planet11_enemy2;
        }

        if ( planet11EnemyCounter == 3){
            planet11EnemyCounter++;
            return planet11_enemy3;
        }

        planet11EnemyCounter = 1 ;

        return planet11_enemy4;

    }

    Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }
}
