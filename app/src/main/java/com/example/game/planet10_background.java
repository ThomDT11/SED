package com.example.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class planet10_background {

    int x = 0, y = 0;

    Bitmap planet10_Background;

    planet10_background(int screenX, int screenY, Resources res) {

        planet10_Background = BitmapFactory.decodeResource(res, R.drawable.bg_10);
        planet10_Background = Bitmap.createScaledBitmap(planet10_Background, screenX, screenY, false);

    }

}