package com.example.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class planet4_background {

    int x = 0, y = 0;
    Bitmap planet4_Background;

    planet4_background(int screenX, int screenY, Resources res) {

        planet4_Background = BitmapFactory.decodeResource(res, R.drawable.bg_4);
        planet4_Background = Bitmap.createScaledBitmap(planet4_Background, screenX, screenY, false);

    }

}
