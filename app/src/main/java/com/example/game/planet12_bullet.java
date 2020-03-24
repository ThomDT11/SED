package com.example.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.game.planet12_game_view.screenRatioX;
import static com.example.game.planet12_game_view.screenRatioY;

public class planet12_bullet {

    int x, y, width, height;
    Bitmap planet12_Bullet;

    planet12_bullet (Resources res){

        planet12_Bullet = BitmapFactory.decodeResource(res, R.drawable.apolaki_sword);

        width = planet12_Bullet.getWidth();
        height = planet12_Bullet.getHeight();

        width /= 1;
        height /= 1;

        width *= (int) screenRatioX;
        height *= (int) screenRatioY;

        planet12_Bullet = Bitmap.createScaledBitmap(planet12_Bullet, width, height, false);


    }
    Rect getCollisionShape () {
        return new Rect(x, y, x + width / 2 , y + height / 2);
    }
}
