package com.example.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class planet14_background {

    int x = 0, y = 0;

    Bitmap planet14_Background;

    planet14_background(int screenX, int screenY, Resources res) {

        planet14_Background = BitmapFactory.decodeResource(res, R.drawable.bg_14);
        planet14_Background = Bitmap.createScaledBitmap(planet14_Background, screenX, screenY, false);

    }

}