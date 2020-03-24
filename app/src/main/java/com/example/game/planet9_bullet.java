package com.example.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.game.planet9_game_view.screenRatioX;
import static com.example.game.planet9_game_view.screenRatioY;

public class planet9_bullet {

    int x, y, width, height;
    Bitmap planet9_Bullet;

    planet9_bullet (Resources res){

        planet9_Bullet = BitmapFactory.decodeResource(res, R.drawable.apolaki_sword);

        width = planet9_Bullet.getWidth();
        height = planet9_Bullet.getHeight();

        width /= 1;
        height /= 1;

        width *= (int) screenRatioX;
        height *= (int) screenRatioY;

        planet9_Bullet = Bitmap.createScaledBitmap(planet9_Bullet, width, height, false);


    }
    Rect getCollisionShape () {
        return new Rect(x, y, x + width / 2, y + height / 2);
    }
}
