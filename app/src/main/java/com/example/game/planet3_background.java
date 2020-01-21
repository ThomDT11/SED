package com.example.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class planet3_background {

    int x = 0, y = 0;
    Bitmap planet3_Background;

    planet3_background(int screenX, int screenY, Resources res) {

        planet3_Background = BitmapFactory.decodeResource(res, R.drawable.bg_3);
        planet3_Background = Bitmap.createScaledBitmap(planet3_Background, screenX, screenY, false);

    }

}
