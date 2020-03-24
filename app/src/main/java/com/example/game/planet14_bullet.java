package com.example.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.game.planet14_game_view.screenRatioX;
import static com.example.game.planet14_game_view.screenRatioY;

public class planet14_bullet {

    int x, y, width, height;
    Bitmap planet14_Bullet;

    planet14_bullet (Resources res){

        planet14_Bullet = BitmapFactory.decodeResource(res, R.drawable.apolaki_sword);

        width = planet14_Bullet.getWidth();
        height = planet14_Bullet.getHeight();

        width /= 1;
        height /= 1;

        width *= (int) screenRatioX;
        height *= (int) screenRatioY;

        planet14_Bullet = Bitmap.createScaledBitmap(planet14_Bullet, width, height, false);


    }
    Rect getCollisionShape () {
        return new Rect(x, y, x + width / 2, y + height / 2);
    }
}
