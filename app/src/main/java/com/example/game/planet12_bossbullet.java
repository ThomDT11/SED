package com.example.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.game.planet12_game_view.screenRatioX;
import static com.example.game.planet12_game_view.screenRatioY;

public class planet12_bossbullet {

    public int speed = 15;
    public boolean wasShot = true;
    int x, y, width, height, planet12BulletCounter = 1;
    Bitmap planet12_bossbullet1, planet12_bossbullet2, planet12_bossbullet3, planet12_bossbullet4;

    planet12_bossbullet(Resources res){

        planet12_bossbullet1 = BitmapFactory.decodeResource(res, R.drawable.b3_shot_1);
        planet12_bossbullet2 = BitmapFactory.decodeResource(res, R.drawable.b3_shot_1);
        planet12_bossbullet3 = BitmapFactory.decodeResource(res, R.drawable.b3_shot_1);
        planet12_bossbullet4 = BitmapFactory.decodeResource(res, R.drawable.b3_shot_1);

        width = planet12_bossbullet1.getWidth();
        height = planet12_bossbullet1.getHeight();

        width /= 2;
        height /= 2;

        width *= (int) screenRatioX;
        height *= (int) screenRatioY;

        planet12_bossbullet1 = Bitmap.createScaledBitmap(planet12_bossbullet1, width, height, false);
        planet12_bossbullet2 = Bitmap.createScaledBitmap(planet12_bossbullet2, width, height, false);
        planet12_bossbullet3 = Bitmap.createScaledBitmap(planet12_bossbullet3, width, height, false);
        planet12_bossbullet4 = Bitmap.createScaledBitmap(planet12_bossbullet4, width, height, false);

        y = -height;

    }


    Bitmap getplanet12_bossbullet(){

        if ( planet12BulletCounter == 1){
            planet12BulletCounter++;
            return planet12_bossbullet1;
        }

        if ( planet12BulletCounter == 2){
            planet12BulletCounter++;
            return planet12_bossbullet2;
        }

        if ( planet12BulletCounter == 3){
            planet12BulletCounter++;
            return planet12_bossbullet3;
        }

        planet12BulletCounter = 1 ;

        return planet12_bossbullet4;

    }

    Rect getCollisionShape () {
        return new Rect(x, y, x + width / 2, y + height / 2 );
    }
}
