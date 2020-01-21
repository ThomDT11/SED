package com.example.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class planet13_background {

    int x = 0, y = 0;

    Bitmap planet13_Background;

    planet13_background(int screenX, int screenY, Resources res) {

        planet13_Background = BitmapFactory.decodeResource(res, R.drawable.bg_13);
        planet13_Background = Bitmap.createScaledBitmap(planet13_Background, screenX, screenY, false);

    }

}