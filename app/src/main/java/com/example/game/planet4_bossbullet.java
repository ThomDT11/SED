package com.example.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.game.planet4_game_view.screenRatioX;
import static com.example.game.planet4_game_view.screenRatioY;

public class planet4_bossbullet {

    public int speed = 15;
    public boolean wasShot = true;
    int x, y, width, height, planet4BulletCounter = 1;
    Bitmap planet4_bossbullet1, planet4_bossbullet2, planet4_bossbullet3, planet4_bossbullet4;

    planet4_bossbullet(Resources res){

        planet4_bossbullet1 = BitmapFactory.decodeResource(res, R.drawable.b1_shot);
        planet4_bossbullet2 = BitmapFactory.decodeResource(res, R.drawable.b1_shot);
        planet4_bossbullet3 = BitmapFactory.decodeResource(res, R.drawable.b1_shot);
        planet4_bossbullet4 = BitmapFactory.decodeResource(res, R.drawable.b1_shot);

        width = planet4_bossbullet1.getWidth();
        height = planet4_bossbullet1.getHeight();

        width /= 2;
        height /= 2;

        width *= (int) screenRatioX;
        height *= (int) screenRatioY;

        planet4_bossbullet1 = Bitmap.createScaledBitmap(planet4_bossbullet1, width, height, false);
        planet4_bossbullet2 = Bitmap.createScaledBitmap(planet4_bossbullet2, width, height, false);
        planet4_bossbullet3 = Bitmap.createScaledBitmap(planet4_bossbullet3, width, height, false);
        planet4_bossbullet4 = Bitmap.createScaledBitmap(planet4_bossbullet4, width, height, false);

        y = -height;

    }


    Bitmap getplanet4_bossbullet(){

        if ( planet4BulletCounter == 1){
            planet4BulletCounter++;
            return planet4_bossbullet1;
        }

        if ( planet4BulletCounter == 2){
            planet4BulletCounter++;
            return planet4_bossbullet2;
        }

        if ( planet4BulletCounter == 3){
            planet4BulletCounter++;
            return planet4_bossbullet3;
        }

        planet4BulletCounter = 1 ;

        return planet4_bossbullet4;

    }

    Rect getCollisionShape () {
        return new Rect(x, y, x + width / 2, y + height / 2 );
    }
}
