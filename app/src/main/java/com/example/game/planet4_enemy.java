package com.example.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.game.earth_game_view.screenRatioX;
import static com.example.game.earth_game_view.screenRatioY;

public class planet4_enemy {

    public int speed = 10;
    public boolean wasShot = true;
    int x, y, width, height, planet4EnemyCounter = 1;
    Bitmap planet4_enemy1, planet4_enemy2, planet4_enemybullet1, planet4_enemybullet2;

    planet4_enemy (Resources res){

        planet4_enemy1 = BitmapFactory.decodeResource(res, R.drawable.b1_f1);
        planet4_enemy2 = BitmapFactory.decodeResource(res, R.drawable.b1_f2);
        planet4_enemybullet1 = BitmapFactory.decodeResource(res, R.drawable.b1_shot);
        planet4_enemybullet2 = BitmapFactory.decodeResource(res, R.drawable.b1_shot);

        width = planet4_enemy1.getWidth();
        height = planet4_enemy1.getHeight();

        width /= 0.8;
        height /= 0.8;

        width *= (int) screenRatioX;
        height *= (int) screenRatioY;

        planet4_enemy1 = Bitmap.createScaledBitmap(planet4_enemy1, width, height, false);
        planet4_enemy2 = Bitmap.createScaledBitmap(planet4_enemy2, width, height, false);
        planet4_enemybullet1 = Bitmap.createScaledBitmap(planet4_enemybullet1, width, height, false);
        planet4_enemybullet2 = Bitmap.createScaledBitmap(planet4_enemybullet2, width, height, false);

        y = -height;

    }


    Bitmap getplanet4_enemy(){

        if ( planet4EnemyCounter == 1){
            planet4EnemyCounter++;
            return planet4_enemy1;
        }

        if ( planet4EnemyCounter == 2){
            planet4EnemyCounter++;
            return planet4_enemy2;
        }

        if ( planet4EnemyCounter == 3){
            planet4EnemyCounter++;
            return planet4_enemybullet1;
        }

        planet4EnemyCounter = 1 ;

        return planet4_enemybullet2;

    }

    Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }
}
