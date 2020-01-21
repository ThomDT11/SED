package com.example.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class planet6_background {

    int x = 0, y = 0;

    Bitmap planet6_Background;

    planet6_background(int screenX, int screenY, Resources res) {

        planet6_Background = BitmapFactory.decodeResource(res, R.drawable.bg_6);
        planet6_Background = Bitmap.createScaledBitmap(planet6_Background, screenX, screenY, false);

    }

}