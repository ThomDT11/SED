package com.example.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.game.planet8_game_view.screenRatioX;
import static com.example.game.planet8_game_view.screenRatioY;

public class planet8_bullet {

    int x, y, width, height;
    Bitmap planet8_Bullet;

    planet8_bullet (Resources res){

        planet8_Bullet = BitmapFactory.decodeResource(res, R.drawable.apolaki_sword);

        width = planet8_Bullet.getWidth();
        height = planet8_Bullet.getHeight();

        width /= 1;
        height /= 1;

        width *= (int) screenRatioX;
        height *= (int) screenRatioY;

        planet8_Bullet = Bitmap.createScaledBitmap(planet8_Bullet, width, height, false);


    }
    Rect getCollisionShape () {
        return new Rect(x, y, x + width / 2 , y + height / 2);
    }
}
