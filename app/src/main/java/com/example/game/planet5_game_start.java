package com.example.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.game.planet5_game_view.screenRatioX;
import static com.example.game.planet5_game_view.screenRatioY;


public class planet5_game_start {

    int toShoot = 0;
    boolean isGoingUp = false;
    int x, y, width, height, apolakiCounter = 0, apolakiShootCounter = 1;
    Bitmap planet5_flight1, planet5_flight2, planet5_shoot1,planet5_shoot2,planet5_shoot3,planet5_shoot4,planet5_shoot5, dead;
    private planet5_game_view planet5GameView;


    planet5_game_start(planet5_game_view planet5GameView, int screenY, Resources res){

        this.planet5GameView = planet5GameView;

        planet5_flight1 = BitmapFactory.decodeResource(res, R.drawable.apolaki_small);
        planet5_flight2 = BitmapFactory.decodeResource(res, R.drawable.apolaki_with_aura);

        width = planet5_flight1.getWidth();
        height = planet5_flight1.getHeight();

        width /= 4;
        height /= 4;

        width *= (int) screenRatioX;
        height *= (int) screenRatioY;



        planet5_flight1 = Bitmap.createScaledBitmap(planet5_flight1, width, height, false);
        planet5_flight2 = Bitmap.createScaledBitmap(planet5_flight2, width, height, false);

        planet5_shoot1 = BitmapFactory.decodeResource(res, R.drawable.apolaki_with_aura);
        planet5_shoot2 = BitmapFactory.decodeResource(res, R.drawable.apolaki_with_aura);
        planet5_shoot3 = BitmapFactory.decodeResource(res, R.drawable.apolaki_with_aura);
        planet5_shoot4 = BitmapFactory.decodeResource(res, R.drawable.apolaki_with_aura);
        planet5_shoot5 = BitmapFactory.decodeResource(res, R.drawable.apolaki_with_aura);

        planet5_shoot1= Bitmap.createScaledBitmap(planet5_shoot1, width, height, false);
        planet5_shoot2= Bitmap.createScaledBitmap(planet5_shoot2, width, height, false);
        planet5_shoot3= Bitmap.createScaledBitmap(planet5_shoot3, width, height, false);
        planet5_shoot4= Bitmap.createScaledBitmap(planet5_shoot4, width, height, false);
        planet5_shoot5= Bitmap.createScaledBitmap(planet5_shoot5, width, height, false);



        dead = BitmapFactory.decodeResource(res , R.drawable.apolaki_small);
        dead = Bitmap.createScaledBitmap(dead, width, height, false);

        y = screenY / 2;
        x = (int) (64 * screenRatioX);
    }

    Bitmap getPlanet1_game_start() {

        if (toShoot != 0){

            if (apolakiShootCounter == 1){
                apolakiShootCounter++;
                return planet5_shoot1;
            }

            if (apolakiShootCounter == 2){
                apolakiShootCounter++;
                return planet5_shoot2;
            }

            if (apolakiShootCounter == 3){
                apolakiShootCounter++;
                return planet5_shoot3;
            }

            if (apolakiShootCounter == 4){
                apolakiShootCounter++;
                return planet5_shoot4;
            }

            apolakiShootCounter = 1;
            toShoot--;
            planet5GameView.newBullet();
            return planet5_shoot5;

        }

        if (apolakiCounter == 0 ){
            apolakiCounter ++;
            return planet5_flight1;
        }

        apolakiCounter--;
        return planet5_flight2;
    }


    Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }

    Bitmap getDead(){
        return dead;
    }
}
