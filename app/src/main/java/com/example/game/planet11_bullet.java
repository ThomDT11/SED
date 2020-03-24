package com.example.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.game.planet11_game_view.screenRatioX;
import static com.example.game.planet11_game_view.screenRatioY;

public class planet11_bullet {

    int x, y, width, height;
    Bitmap planet11_Bullet;

    planet11_bullet (Resources res){

        planet11_Bullet = BitmapFactory.decodeResource(res, R.drawable.apolaki_sword);

        width = planet11_Bullet.getWidth();
        height = planet11_Bullet.getHeight();

        width /= 1;
        height /= 1;

        width *= (int) screenRatioX;
        height *= (int) screenRatioY;

        planet11_Bullet = Bitmap.createScaledBitmap(planet11_Bullet, width, height, false);


    }
    Rect getCollisionShape () {
        return new Rect(x, y, x + width / 2, y + height / 2);
    }
}
