package com.example.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class planet11_background {

    int x = 0, y = 0;

    Bitmap planet11_Background;

    planet11_background(int screenX, int screenY, Resources res) {

        planet11_Background = BitmapFactory.decodeResource(res, R.drawable.bg_11);
        planet11_Background = Bitmap.createScaledBitmap(planet11_Background, screenX, screenY, false);

    }

}