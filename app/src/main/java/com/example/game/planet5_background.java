package com.example.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class planet5_background {

    int x = 0, y = 0;

    Bitmap planet5_Background;

    planet5_background(int screenX, int screenY, Resources res) {

        planet5_Background = BitmapFactory.decodeResource(res, R.drawable.bg_5);
        planet5_Background = Bitmap.createScaledBitmap(planet5_Background, screenX, screenY, false);

    }

}