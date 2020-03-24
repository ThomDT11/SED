package com.example.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.game.planet16_game_view.screenRatioX;
import static com.example.game.planet16_game_view.screenRatioY;

public class planet16_bullet {

    int x, y, width, height;
    Bitmap planet16_Bullet;

    planet16_bullet (Resources res){

        planet16_Bullet = BitmapFactory.decodeResource(res, R.drawable.apolaki_sword);

        width = planet16_Bullet.getWidth();
        height = planet16_Bullet.getHeight();

        width /= 1;
        height /= 1;

        width *= (int) screenRatioX;
        height *= (int) screenRatioY;

        planet16_Bullet = Bitmap.createScaledBitmap(planet16_Bullet, width, height, false);


    }
    Rect getCollisionShape () {
        return new Rect(x, y, x + width / 2 , y + height / 2);
    }
}
