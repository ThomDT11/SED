package com.example.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class planet7_background {

    int x = 0, y = 0;

    Bitmap planet7_Background;

    planet7_background(int screenX, int screenY, Resources res) {

        planet7_Background = BitmapFactory.decodeResource(res, R.drawable.bg_7);
        planet7_Background = Bitmap.createScaledBitmap(planet7_Background, screenX, screenY, false);

    }

}