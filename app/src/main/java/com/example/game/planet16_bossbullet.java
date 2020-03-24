package com.example.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.game.planet16_game_view.screenRatioX;
import static com.example.game.planet16_game_view.screenRatioY;

public class planet16_bossbullet {

    public int speed = 15;
    public boolean wasShot = true;
    int x, y, width, height, planet16BulletCounter = 1;
    Bitmap planet16_bossbullet1, planet16_bossbullet2, planet16_bossbullet3, planet16_bossbullet4;

    planet16_bossbullet(Resources res){

        planet16_bossbullet1 = BitmapFactory.decodeResource(res, R.drawable.mayari_shot_1);
        planet16_bossbullet2 = BitmapFactory.decodeResource(res, R.drawable.mayari_shot_1);
        planet16_bossbullet3 = BitmapFactory.decodeResource(res, R.drawable.mayari_shot_1);
        planet16_bossbullet4 = BitmapFactory.decodeResource(res, R.drawable.mayari_shot_1);

        width = planet16_bossbullet1.getWidth();
        height = planet16_bossbullet1.getHeight();

        width /= 2;
        height /= 2;

        width *= (int) screenRatioX;
        height *= (int) screenRatioY;

        planet16_bossbullet1 = Bitmap.createScaledBitmap(planet16_bossbullet1, width, height, false);
        planet16_bossbullet2 = Bitmap.createScaledBitmap(planet16_bossbullet2, width, height, false);
        planet16_bossbullet3 = Bitmap.createScaledBitmap(planet16_bossbullet3, width, height, false);
        planet16_bossbullet4 = Bitmap.createScaledBitmap(planet16_bossbullet4, width, height, false);

        y = -height;

    }


    Bitmap getplanet16_bossbullet(){

        if ( planet16BulletCounter == 1){
            planet16BulletCounter++;
            return planet16_bossbullet1;
        }

        if ( planet16BulletCounter == 2){
            planet16BulletCounter++;
            return planet16_bossbullet2;
        }

        if ( planet16BulletCounter == 3){
            planet16BulletCounter++;
            return planet16_bossbullet3;
        }

        planet16BulletCounter = 1 ;

        return planet16_bossbullet4;

    }

    Rect getCollisionShape () {
        return new Rect(x, y, x + width / 2, y + height / 2 );
    }
}
