package com.example.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.game.planet5_game_view.screenRatioX;
import static com.example.game.planet5_game_view.screenRatioY;

public class planet5_bullet {

    int x, y, width, height;
    Bitmap planet5_Bullet;

    planet5_bullet (Resources res){

        planet5_Bullet = BitmapFactory.decodeResource(res, R.drawable.apolaki_sword);

        width = planet5_Bullet.getWidth();
        height = planet5_Bullet.getHeight();

        width /= 1;
        height /= 1;

        width *= (int) screenRatioX;
        height *= (int) screenRatioY;

        planet5_Bullet = Bitmap.createScaledBitmap(planet5_Bullet, width, height, false);


    }
    Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }
}
