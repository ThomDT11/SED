package com.example.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.game.planet4_game_view.screenRatioX;
import static com.example.game.planet4_game_view.screenRatioY;


public class planet4_boss {

    int toShoot = 0;
    boolean isGoingUp = false;
    int x, y, width, height, bossCounter = 0, bossShootCounter = 1;
    Bitmap planet4boss1, planet4boss2, planet4_shoot1,planet4_shoot2,planet4_shoot3,planet4_shoot4,planet4_shoot5, dead;
    private planet4_game_view planet4GameView;


    planet4_boss(planet4_game_view planet4GameView, int screenY, Resources res){

        this.planet4GameView = planet4GameView;

        planet4boss1 = BitmapFactory.decodeResource(res, R.drawable.b1_f1);
        planet4boss2 = BitmapFactory.decodeResource(res, R.drawable.b1_f2);

        width = planet4boss1.getWidth();
        height = planet4boss1.getHeight();

        width /= 1.5;
        height /= 1.5;

        width *= (int) screenRatioX;
        height *= (int) screenRatioY;



        planet4boss1 = Bitmap.createScaledBitmap(planet4boss1, width, height, false);
        planet4boss1 = Bitmap.createScaledBitmap(planet4boss1, width, height, false);

        planet4_shoot1 = BitmapFactory.decodeResource(res, R.drawable.b1_f2);
        planet4_shoot2 = BitmapFactory.decodeResource(res, R.drawable.b1_f2);
        planet4_shoot3 = BitmapFactory.decodeResource(res, R.drawable.b1_f2);
        planet4_shoot4 = BitmapFactory.decodeResource(res, R.drawable.b1_f2);
        planet4_shoot5 = BitmapFactory.decodeResource(res, R.drawable.b1_f2);

        planet4_shoot1= Bitmap.createScaledBitmap(planet4_shoot1, width, height, false);
        planet4_shoot2= Bitmap.createScaledBitmap(planet4_shoot2, width, height, false);
        planet4_shoot3= Bitmap.createScaledBitmap(planet4_shoot3, width, height, false);
        planet4_shoot4= Bitmap.createScaledBitmap(planet4_shoot4, width, height, false);
        planet4_shoot5= Bitmap.createScaledBitmap(planet4_shoot5, width, height, false);



        dead = BitmapFactory.decodeResource(res , R.drawable.b1_f1);
        dead = Bitmap.createScaledBitmap(dead, width, height, false);

        y = screenY / 2;
        x = (int) (1100 * screenRatioX);
    }

    Bitmap getPlanet4_boss() {

        if (toShoot != 0){

            if (bossShootCounter == 1){
                bossShootCounter++;
                return planet4_shoot1;
            }

            if (bossShootCounter == 2){
                bossShootCounter++;
                return planet4_shoot2;
            }

            if (bossShootCounter == 3){
                bossShootCounter++;
                return planet4_shoot3;
            }

            if (bossShootCounter == 4){
                bossShootCounter++;
                return planet4_shoot4;
            }

            bossShootCounter = 1;
            toShoot--;
            planet4GameView.newBullet();
            return planet4_shoot5;

        }

        if (bossCounter == 0 ){
            bossCounter ++;
            return planet4boss1;
        }

        bossCounter--;
        return planet4boss1;
    }


    Rect getCollisionShape () {
        return new Rect(x, y, x + width / 2, y + height / 2);
    }

    Bitmap getDead(){
        return dead;
    }
}
