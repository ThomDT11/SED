package com.example.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.game.planet13_game_view.screenRatioX;
import static com.example.game.planet13_game_view.screenRatioY;

public class planet13_bullet {

    int x, y, width, height;
    Bitmap planet13_Bullet;

    planet13_bullet (Resources res){

        planet13_Bullet = BitmapFactory.decodeResource(res, R.drawable.apolaki_sword);

        width = planet13_Bullet.getWidth();
        height = planet13_Bullet.getHeight();

        width /= 1;
        height /= 1;

        width *= (int) screenRatioX;
        height *= (int) screenRatioY;

        planet13_Bullet = Bitmap.createScaledBitmap(planet13_Bullet, width, height, false);


    }
    Rect getCollisionShape () {
        return new Rect(x, y, x + width / 2, y + height / 2);
    }
}
