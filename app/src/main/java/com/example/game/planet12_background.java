package com.example.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class planet12_background {

    int x = 0, y = 0;

    Bitmap planet12_Background;

    planet12_background(int screenX, int screenY, Resources res) {

        planet12_Background = BitmapFactory.decodeResource(res, R.drawable.bg_12);
        planet12_Background = Bitmap.createScaledBitmap(planet12_Background, screenX, screenY, false);

    }

}