package com.example.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.game.planet8_game_view.screenRatioX;
import static com.example.game.planet8_game_view.screenRatioY;

public class planet8_bossbullet {

    public int speed = 15;
    public boolean wasShot = true;
    int x, y, width, height, planet8BulletCounter = 1;
    Bitmap planet8_bossbullet1, planet8_bossbullet2, planet8_bossbullet3, planet8_bossbullet4;

    planet8_bossbullet(Resources res){

        planet8_bossbullet1 = BitmapFactory.decodeResource(res, R.drawable.b2_shot);
        planet8_bossbullet2 = BitmapFactory.decodeResource(res, R.drawable.b2_shot);
        planet8_bossbullet3 = BitmapFactory.decodeResource(res, R.drawable.b2_shot);
        planet8_bossbullet4 = BitmapFactory.decodeResource(res, R.drawable.b2_shot);

        width = planet8_bossbullet1.getWidth();
        height = planet8_bossbullet1.getHeight();

        width /= 2;
        height /= 2;

        width *= (int) screenRatioX;
        height *= (int) screenRatioY;

        planet8_bossbullet1 = Bitmap.createScaledBitmap(planet8_bossbullet1, width, height, false);
        planet8_bossbullet2 = Bitmap.createScaledBitmap(planet8_bossbullet2, width, height, false);
        planet8_bossbullet3 = Bitmap.createScaledBitmap(planet8_bossbullet3, width, height, false);
        planet8_bossbullet4 = Bitmap.createScaledBitmap(planet8_bossbullet4, width, height, false);

        y = -height;

    }


    Bitmap getplanet8_bossbullet(){

        if ( planet8BulletCounter == 1){
            planet8BulletCounter++;
            return planet8_bossbullet1;
        }

        if ( planet8BulletCounter == 2){
            planet8BulletCounter++;
            return planet8_bossbullet2;
        }

        if ( planet8BulletCounter == 3){
            planet8BulletCounter++;
            return planet8_bossbullet3;
        }

        planet8BulletCounter = 1 ;

        return planet8_bossbullet4;

    }

    Rect getCollisionShape () {
        return new Rect(x, y, x + width / 2, y + height / 2 );
    }
}
