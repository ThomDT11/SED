package com.example.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.game.planet4_game_view.screenRatioX;
import static com.example.game.planet4_game_view.screenRatioY;

public class planet4_bullet {

    int x, y, width, height;
    Bitmap planet4_Bullet;

    planet4_bullet (Resources res){

        planet4_Bullet = BitmapFactory.decodeResource(res, R.drawable.apolaki_sword);

        width = planet4_Bullet.getWidth();
        height = planet4_Bullet.getHeight();

        width /= 1;
        height /= 1;

        width *= (int) screenRatioX;
        height *= (int) screenRatioY;

        planet4_Bullet = Bitmap.createScaledBitmap(planet4_Bullet, width, height, false);


    }
    Rect getCollisionShape () {
        return new Rect(x, y, x + width , y + height / 2);
    }
}
