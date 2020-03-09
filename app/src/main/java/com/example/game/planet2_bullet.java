package com.example.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.game.planet2_game_view.screenRatioX;
import static com.example.game.planet2_game_view.screenRatioY;

public class planet2_bullet {

    int x, y, width, height;
    Bitmap planet2_Bullet;

    planet2_bullet (Resources res){

        planet2_Bullet = BitmapFactory.decodeResource(res, R.drawable.shot_1);

        width = planet2_Bullet.getWidth();
        height = planet2_Bullet.getHeight();

        width /= 3;
        height /= 3;

        width *= (int) screenRatioX;
        height *= (int) screenRatioY;

        planet2_Bullet = Bitmap.createScaledBitmap(planet2_Bullet, width, height, false);


    }
    Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }
}
