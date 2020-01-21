package com.example.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class planet15_background {

    int x = 0, y = 0;

    Bitmap planet15_Background;

    planet15_background(int screenX, int screenY, Resources res) {

        planet15_Background = BitmapFactory.decodeResource(res, R.drawable.bg_15);
        planet15_Background = Bitmap.createScaledBitmap(planet15_Background, screenX, screenY, false);

    }

}