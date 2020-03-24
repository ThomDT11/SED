package com.example.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.game.planet7_game_view.screenRatioX;
import static com.example.game.planet7_game_view.screenRatioY;

public class planet7_bullet {

    int x, y, width, height;
    Bitmap planet7_Bullet;

    planet7_bullet (Resources res){

        planet7_Bullet = BitmapFactory.decodeResource(res, R.drawable.apolaki_sword);

        width = planet7_Bullet.getWidth();
        height = planet7_Bullet.getHeight();

        width /= 1;
        height /= 1;

        width *= (int) screenRatioX;
        height *= (int) screenRatioY;

        planet7_Bullet = Bitmap.createScaledBitmap(planet7_Bullet, width, height, false);


    }
    Rect getCollisionShape () {
        return new Rect(x, y, x + width / 2, y + height / 2);
    }
}
