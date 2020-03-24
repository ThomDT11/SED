package com.example.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.game.planet15_game_view.screenRatioX;
import static com.example.game.planet15_game_view.screenRatioY;

public class planet15_bullet {

    int x, y, width, height;
    Bitmap planet15_Bullet;

    planet15_bullet (Resources res){

        planet15_Bullet = BitmapFactory.decodeResource(res, R.drawable.apolaki_sword);

        width = planet15_Bullet.getWidth();
        height = planet15_Bullet.getHeight();

        width /= 1;
        height /= 1;

        width *= (int) screenRatioX;
        height *= (int) screenRatioY;

        planet15_Bullet = Bitmap.createScaledBitmap(planet15_Bullet, width, height, false);


    }
    Rect getCollisionShape () {
        return new Rect(x, y, x + width / 2, y + height / 2);
    }
}
