package com.example.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.game.planet6_game_view.screenRatioX;
import static com.example.game.planet6_game_view.screenRatioY;

public class planet6_bullet {

    int x, y, width, height;
    Bitmap planet6_Bullet;

    planet6_bullet (Resources res){

        planet6_Bullet = BitmapFactory.decodeResource(res, R.drawable.apolaki_sword);

        width = planet6_Bullet.getWidth();
        height = planet6_Bullet.getHeight();

        width /= 1;
        height /= 1;

        width *= (int) screenRatioX;
        height *= (int) screenRatioY;

        planet6_Bullet = Bitmap.createScaledBitmap(planet6_Bullet, width, height, false);


    }
    Rect getCollisionShape () {
        return new Rect(x, y, x + width / 2, y + height / 2);
    }
}
