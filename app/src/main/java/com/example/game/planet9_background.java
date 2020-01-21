package com.example.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class planet9_background {

    int x = 0, y = 0;

    Bitmap planet9_Background;

    planet9_background(int screenX, int screenY, Resources res) {

        planet9_Background = BitmapFactory.decodeResource(res, R.drawable.bg_9);
        planet9_Background = Bitmap.createScaledBitmap(planet9_Background, screenX, screenY, false);

    }

}