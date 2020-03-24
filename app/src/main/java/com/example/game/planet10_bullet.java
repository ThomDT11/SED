package com.example.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.game.planet10_game_view.screenRatioX;
import static com.example.game.planet10_game_view.screenRatioY;

public class planet10_bullet {

    int x, y, width, height;
    Bitmap planet10_Bullet;

    planet10_bullet (Resources res){

        planet10_Bullet = BitmapFactory.decodeResource(res, R.drawable.apolaki_sword);

        width = planet10_Bullet.getWidth();
        height = planet10_Bullet.getHeight();

        width /= 1;
        height /= 1;

        width *= (int) screenRatioX;
        height *= (int) screenRatioY;

        planet10_Bullet = Bitmap.createScaledBitmap(planet10_Bullet, width, height, false);


    }
    Rect getCollisionShape () {
        return new Rect(x, y, x + width / 2, y + height / 2);
    }
}
