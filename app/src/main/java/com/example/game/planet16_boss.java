package com.example.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.game.planet16_game_view.screenRatioX;
import static com.example.game.planet16_game_view.screenRatioY;


public class planet16_boss {

    int toShoot = 0;
    boolean isGoingUp = false;
    int x, y, width, height, bossCounter = 0, bossShootCounter = 1;
    Bitmap planet16boss1, planet16boss2, planet16_shoot1,planet16_shoot2,planet16_shoot3,planet16_shoot4,planet16_shoot5, dead;
    private planet16_game_view planet16GameView;


    planet16_boss(planet16_game_view planet16GameView, int screenY, Resources res){

        this.planet16GameView = planet16GameView;

        planet16boss1 = BitmapFactory.decodeResource(res, R.drawable.mayari_f1);
        planet16boss2 = BitmapFactory.decodeResource(res, R.drawable.mayari_with_glow);

        width = planet16boss1.getWidth();
        height = planet16boss1.getHeight();

        width /= 8;
        height /= 8;

        width *= (int) screenRatioX;
        height *= (int) screenRatioY;



        planet16boss1 = Bitmap.createScaledBitmap(planet16boss1, width, height, false);
        planet16boss1 = Bitmap.createScaledBitmap(planet16boss1, width, height, false);

        planet16_shoot1 = BitmapFactory.decodeResource(res, R.drawable.mayari_with_glow);
        planet16_shoot2 = BitmapFactory.decodeResource(res, R.drawable.mayari_with_glow);
        planet16_shoot3 = BitmapFactory.decodeResource(res, R.drawable.mayari_with_glow);
        planet16_shoot4 = BitmapFactory.decodeResource(res, R.drawable.mayari_with_glow);
        planet16_shoot5 = BitmapFactory.decodeResource(res, R.drawable.mayari_with_glow);

        planet16_shoot1= Bitmap.createScaledBitmap(planet16_shoot1, width, height, false);
        planet16_shoot2= Bitmap.createScaledBitmap(planet16_shoot2, width, height, false);
        planet16_shoot3= Bitmap.createScaledBitmap(planet16_shoot3, width, height, false);
        planet16_shoot4= Bitmap.createScaledBitmap(planet16_shoot4, width, height, false);
        planet16_shoot5= Bitmap.createScaledBitmap(planet16_shoot5, width, height, false);



        dead = BitmapFactory.decodeResource(res , R.drawable.mayari_f1);
        dead = Bitmap.createScaledBitmap(dead, width, height, false);

        y = screenY / 2;
        x = (int) (1350 * screenRatioX);
    }

    Bitmap getplanet16_boss() {

        if (toShoot != 0){

            if (bossShootCounter == 1){
                bossShootCounter++;
                return planet16_shoot1;
            }

            if (bossShootCounter == 2){
                bossShootCounter++;
                return planet16_shoot2;
            }

            if (bossShootCounter == 3){
                bossShootCounter++;
                return planet16_shoot3;
            }

            if (bossShootCounter == 4){
                bossShootCounter++;
                return planet16_shoot4;
            }

            bossShootCounter = 1;
            toShoot--;
            planet16GameView.newBullet();
            return planet16_shoot5;

        }

        if (bossCounter == 0 ){
            bossCounter ++;
            return planet16boss1;
        }

        bossCounter--;
        return planet16boss1;
    }


    Rect getCollisionShape () {
        return new Rect(x, y, x + width / 2, y + height / 2);
    }

    Bitmap getDead(){
        return dead;
    }
}
