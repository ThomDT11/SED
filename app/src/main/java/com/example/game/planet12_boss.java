package com.example.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.game.planet12_game_view.screenRatioX;
import static com.example.game.planet12_game_view.screenRatioY;


public class planet12_boss {

    int toShoot = 0;
    boolean isGoingUp = false;
    int x, y, width, height, bossCounter = 0, bossShootCounter = 1;
    Bitmap planet12boss1, planet12boss2, planet12_shoot1,planet12_shoot2,planet12_shoot3,planet12_shoot4,planet12_shoot5, dead;
    private planet12_game_view planet12GameView;


    planet12_boss(planet12_game_view planet12GameView, int screenY, Resources res){

        this.planet12GameView = planet12GameView;

        planet12boss1 = BitmapFactory.decodeResource(res, R.drawable.b3_f1);
        planet12boss2 = BitmapFactory.decodeResource(res, R.drawable.b3_f2);

        width = planet12boss1.getWidth();
        height = planet12boss1.getHeight();

        width /= 1.5;
        height /= 1.5;

        width *= (int) screenRatioX;
        height *= (int) screenRatioY;



        planet12boss1 = Bitmap.createScaledBitmap(planet12boss1, width, height, false);
        planet12boss1 = Bitmap.createScaledBitmap(planet12boss1, width, height, false);

        planet12_shoot1 = BitmapFactory.decodeResource(res, R.drawable.b3_f2);
        planet12_shoot2 = BitmapFactory.decodeResource(res, R.drawable.b3_f2);
        planet12_shoot3 = BitmapFactory.decodeResource(res, R.drawable.b3_f2);
        planet12_shoot4 = BitmapFactory.decodeResource(res, R.drawable.b3_f2);
        planet12_shoot5 = BitmapFactory.decodeResource(res, R.drawable.b3_f2);

        planet12_shoot1= Bitmap.createScaledBitmap(planet12_shoot1, width, height, false);
        planet12_shoot2= Bitmap.createScaledBitmap(planet12_shoot2, width, height, false);
        planet12_shoot3= Bitmap.createScaledBitmap(planet12_shoot3, width, height, false);
        planet12_shoot4= Bitmap.createScaledBitmap(planet12_shoot4, width, height, false);
        planet12_shoot5= Bitmap.createScaledBitmap(planet12_shoot5, width, height, false);



        dead = BitmapFactory.decodeResource(res , R.drawable.b3_f1);
        dead = Bitmap.createScaledBitmap(dead, width, height, false);

        y = screenY / 2;
        x = (int) (1300 * screenRatioX);
    }

    Bitmap getplanet12_boss() {

        if (toShoot != 0){

            if (bossShootCounter == 1){
                bossShootCounter++;
                return planet12_shoot1;
            }

            if (bossShootCounter == 2){
                bossShootCounter++;
                return planet12_shoot2;
            }

            if (bossShootCounter == 3){
                bossShootCounter++;
                return planet12_shoot3;
            }

            if (bossShootCounter == 4){
                bossShootCounter++;
                return planet12_shoot4;
            }

            bossShootCounter = 1;
            toShoot--;
            planet12GameView.newBullet();
            return planet12_shoot5;

        }

        if (bossCounter == 0 ){
            bossCounter ++;
            return planet12boss1;
        }

        bossCounter--;
        return planet12boss1;
    }


    Rect getCollisionShape () {
        return new Rect(x, y, x + width / 2, y + height / 2);
    }

    Bitmap getDead(){
        return dead;
    }
}
