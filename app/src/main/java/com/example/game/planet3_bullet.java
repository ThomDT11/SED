package com.example.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.game.planet3_game_view.screenRatioX;
import static com.example.game.planet3_game_view.screenRatioY;

public class planet3_bullet {

    int x, y, width, height;
    Bitmap planet3_Bullet;

    planet3_bullet (Resources res){

        planet3_Bullet = BitmapFactory.decodeResource(res, R.drawable.apolaki_sword);

        width = planet3_Bullet.getWidth();
        height = planet3_Bullet.getHeight();

        width /= 1;
        height /= 1;

        width *= (int) screenRatioX;
        height *= (int) screenRatioY;

        planet3_Bullet = Bitmap.createScaledBitmap(planet3_Bullet, width, height, false);


    }
    Rect getCollisionShape () {
        return new Rect(x, y, x + width / 2, y + height / 2);
    }
}
