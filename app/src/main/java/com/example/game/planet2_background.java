package com.example.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class planet2_background {


    int x = 0, y = 0;
    Bitmap planet2_Background;

    planet2_background(int screenX, int screenY, Resources res) {

        planet2_Background = BitmapFactory.decodeResource(res, R.drawable.bg_2);
        planet2_Background = Bitmap.createScaledBitmap(planet2_Background, screenX, screenY, false);

    }

}
