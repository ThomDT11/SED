package com.example.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class planet8_background {

    int x = 0, y = 0;

    Bitmap planet8_Background;

    planet8_background(int screenX, int screenY, Resources res) {

        planet8_Background = BitmapFactory.decodeResource(res, R.drawable.bg_8);
        planet8_Background = Bitmap.createScaledBitmap(planet8_Background, screenX, screenY, false);

    }

}