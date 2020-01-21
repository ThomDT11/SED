package com.example.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class planet16_background {

    int x = 0, y = 0;

    Bitmap planet16_Background;

    planet16_background(int screenX, int screenY, Resources res) {

        planet16_Background = BitmapFactory.decodeResource(res, R.drawable.bg_16);
        planet16_Background = Bitmap.createScaledBitmap(planet16_Background, screenX, screenY, false);

    }

}