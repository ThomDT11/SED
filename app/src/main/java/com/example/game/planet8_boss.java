package com.example.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.game.planet8_game_view.screenRatioX;
import static com.example.game.planet8_game_view.screenRatioY;


public class planet8_boss {

    int toShoot = 0;
    boolean isGoingUp = false;
    int x, y, width, height, bossCounter = 0, bossShootCounter = 1;
    Bitmap planet8boss1, planet8boss2, planet8_shoot1,planet8_shoot2,planet8_shoot3,planet8_shoot4,planet8_shoot5, dead;
    private planet8_game_view planet8GameView;


    planet8_boss(planet8_game_view planet8GameView, int screenY, Resources res){

        this.planet8GameView = planet8GameView;

        planet8boss1 = BitmapFactory.decodeResource(res, R.drawable.b2_f1);
        planet8boss2 = BitmapFactory.decodeResource(res, R.drawable.b2_f3);

        width = planet8boss1.getWidth();
        height = planet8boss1.getHeight();

        width /= 1.5;
        height /= 1.5;

        width *= (int) screenRatioX;
        height *= (int) screenRatioY;



        planet8boss1 = Bitmap.createScaledBitmap(planet8boss1, width, height, false);
        planet8boss1 = Bitmap.createScaledBitmap(planet8boss1, width, height, false);

        planet8_shoot1 = BitmapFactory.decodeResource(res, R.drawable.b2_f3);
        planet8_shoot2 = BitmapFactory.decodeResource(res, R.drawable.b2_f3);
        planet8_shoot3 = BitmapFactory.decodeResource(res, R.drawable.b2_f3);
        planet8_shoot4 = BitmapFactory.decodeResource(res, R.drawable.b2_f3);
        planet8_shoot5 = BitmapFactory.decodeResource(res, R.drawable.b2_f3);

        planet8_shoot1= Bitmap.createScaledBitmap(planet8_shoot1, width, height, false);
        planet8_shoot2= Bitmap.createScaledBitmap(planet8_shoot2, width, height, false);
        planet8_shoot3= Bitmap.createScaledBitmap(planet8_shoot3, width, height, false);
        planet8_shoot4= Bitmap.createScaledBitmap(planet8_shoot4, width, height, false);
        planet8_shoot5= Bitmap.createScaledBitmap(planet8_shoot5, width, height, false);



        dead = BitmapFactory.decodeResource(res , R.drawable.b2_f1);
        dead = Bitmap.createScaledBitmap(dead, width, height, false);

        y = screenY / 2;
        x = (int) (1300 * screenRatioX);
    }

    Bitmap getplanet8_boss() {

        if (toShoot != 0){

            if (bossShootCounter == 1){
                bossShootCounter++;
                return planet8_shoot1;
            }

            if (bossShootCounter == 2){
                bossShootCounter++;
                return planet8_shoot2;
            }

            if (bossShootCounter == 3){
                bossShootCounter++;
                return planet8_shoot3;
            }

            if (bossShootCounter == 4){
                bossShootCounter++;
                return planet8_shoot4;
            }

            bossShootCounter = 1;
            toShoot--;
            planet8GameView.newBullet();
            return planet8_shoot5;

        }

        if (bossCounter == 0 ){
            bossCounter ++;
            return planet8boss1;
        }

        bossCounter--;
        return planet8boss1;
    }


    Rect getCollisionShape () {
        return new Rect(x, y, x + width / 2, y + height / 2);
    }

    Bitmap getDead(){
        return dead;
    }
}
