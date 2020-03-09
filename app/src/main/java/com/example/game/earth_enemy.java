package com.example.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.game.earth_game_view.screenRatioX;
import static com.example.game.earth_game_view.screenRatioY;

public class earth_enemy {

    public int speed = 10;
    public boolean wasShot = true;
    int x, y, width, height, earthEnemyCounter = 1;
    Bitmap earth_enemy1, earth_enemy2, earth_enemy3, earth_enemy4;

    earth_enemy (Resources res){

        earth_enemy1 = BitmapFactory.decodeResource(res, R.drawable.e1);
        earth_enemy2 = BitmapFactory.decodeResource(res, R.drawable.e1);
        earth_enemy3 = BitmapFactory.decodeResource(res, R.drawable.e1);
        earth_enemy4 = BitmapFactory.decodeResource(res, R.drawable.e1);

        width = earth_enemy1.getWidth();
        height = earth_enemy1.getHeight();

        width /= 0.8;
        height /= 0.8;

        width *= (int) screenRatioX;
        height *= (int) screenRatioY;

        earth_enemy1 = Bitmap.createScaledBitmap(earth_enemy1, width, height, false);
        earth_enemy2 = Bitmap.createScaledBitmap(earth_enemy2, width, height, false);
        earth_enemy3 = Bitmap.createScaledBitmap(earth_enemy3, width, height, false);
        earth_enemy4 = Bitmap.createScaledBitmap(earth_enemy4, width, height, false);

        y = -height;

    }


    Bitmap getearth_enemy(){

        if ( earthEnemyCounter == 1){
            earthEnemyCounter++;
            return earth_enemy1;
        }

        if ( earthEnemyCounter == 2){
            earthEnemyCounter++;
            return earth_enemy2;
        }

        if ( earthEnemyCounter == 3){
            earthEnemyCounter++;
            return earth_enemy3;
        }

        earthEnemyCounter = 1 ;

        return earth_enemy4;

    }

    Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }
}
