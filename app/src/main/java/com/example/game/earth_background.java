package com.example.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class earth_background {

    int x = 0, y = 0;
    Bitmap earth_Background;

    earth_background(int screenX, int screenY, Resources res) {

        earth_Background = BitmapFactory.decodeResource(res, R.drawable.bg_earth2);
        earth_Background = Bitmap.createScaledBitmap(earth_Background, screenX, screenY, false);



    }

}
