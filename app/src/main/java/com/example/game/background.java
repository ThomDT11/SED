package com.example.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class background {

    int x = 0, y = 0;
    Bitmap background;

    background(int screenX, int screenY, Resources res) {

        background = BitmapFactory.decodeResource(res, R.drawable.bg_earth2);
        background = Bitmap.createScaledBitmap(background, screenX, screenY, false);



    }

}
