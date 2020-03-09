package com.example.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.game.earth_game_view.screenRatioX;
import static com.example.game.earth_game_view.screenRatioY;

public class earth_bullet {

    int x, y, width, height;
    Bitmap earth_Bullet;

    earth_bullet (Resources res){

        earth_Bullet = BitmapFactory.decodeResource(res, R.drawable.apolaki_sword);

        width = earth_Bullet.getWidth();
        height = earth_Bullet.getHeight();

        width /= 1;
        height /= 1;

        width *= (int) screenRatioX;
        height *= (int) screenRatioY;

        earth_Bullet = Bitmap.createScaledBitmap(earth_Bullet, width, height, false);


    }
    Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }
}
