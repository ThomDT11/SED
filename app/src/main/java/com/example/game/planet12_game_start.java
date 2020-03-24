package com.example.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.game.planet12_game_view.screenRatioX;
import static com.example.game.planet12_game_view.screenRatioY;


public class planet12_game_start {

    int toShoot = 0;
    boolean isGoingUp = false;
    int x, y, width, height, apolakiCounter = 0, apolakiShootCounter = 1;
    Bitmap planet12_flight1, planet12_flight2, planet12_shoot1,planet12_shoot2,planet12_shoot3,planet12_shoot4,planet12_shoot5, dead;
    private planet12_game_view planet12GameView;


    planet12_game_start(planet12_game_view planet12GameView, int screenY, Resources res){

        this.planet12GameView = planet12GameView;

        planet12_flight1 = BitmapFactory.decodeResource(res, R.drawable.apolaki_small);
        planet12_flight2 = BitmapFactory.decodeResource(res, R.drawable.apolaki_with_aura);

        width = planet12_flight1.getWidth();
        height = planet12_flight1.getHeight();

        width /= 4;
        height /= 4;

        width *= (int) screenRatioX;
        height *= (int) screenRatioY;



        planet12_flight1 = Bitmap.createScaledBitmap(planet12_flight1, width, height, false);
        planet12_flight2 = Bitmap.createScaledBitmap(planet12_flight2, width, height, false);

        planet12_shoot1 = BitmapFactory.decodeResource(res, R.drawable.apolaki_with_aura);
        planet12_shoot2 = BitmapFactory.decodeResource(res, R.drawable.apolaki_with_aura);
        planet12_shoot3 = BitmapFactory.decodeResource(res, R.drawable.apolaki_with_aura);
        planet12_shoot4 = BitmapFactory.decodeResource(res, R.drawable.apolaki_with_aura);
        planet12_shoot5 = BitmapFactory.decodeResource(res, R.drawable.apolaki_with_aura);

        planet12_shoot1= Bitmap.createScaledBitmap(planet12_shoot1, width, height, false);
        planet12_shoot2= Bitmap.createScaledBitmap(planet12_shoot2, width, height, false);
        planet12_shoot3= Bitmap.createScaledBitmap(planet12_shoot3, width, height, false);
        planet12_shoot4= Bitmap.createScaledBitmap(planet12_shoot4, width, height, false);
        planet12_shoot5= Bitmap.createScaledBitmap(planet12_shoot5, width, height, false);



        dead = BitmapFactory.decodeResource(res , R.drawable.apolaki_small);
        dead = Bitmap.createScaledBitmap(dead, width, height, false);

        y = screenY / 2;
        x = (int) (64 * screenRatioX);
    }

    Bitmap getPlanet1_game_start() {

        if (toShoot != 0){

            if (apolakiShootCounter == 1){
                apolakiShootCounter++;
                return planet12_shoot1;
            }

            if (apolakiShootCounter == 2){
                apolakiShootCounter++;
                return planet12_shoot2;
            }

            if (apolakiShootCounter == 3){
                apolakiShootCounter++;
                return planet12_shoot3;
            }

            if (apolakiShootCounter == 4){
                apolakiShootCounter++;
                return planet12_shoot4;
            }

            apolakiShootCounter = 1;
            toShoot--;
            planet12GameView.newBullet();
            return planet12_shoot5;

        }

        if (apolakiCounter == 0 ){
            apolakiCounter ++;
            return planet12_flight1;
        }

        apolakiCounter--;
        return planet12_flight2;
    }


    Rect getCollisionShape () {
        return new Rect(x, y, x + width / 2, y + height / 2);
    }

    Bitmap getDead(){
        return dead;
    }
}
