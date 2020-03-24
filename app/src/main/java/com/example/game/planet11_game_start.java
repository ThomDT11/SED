package com.example.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.game.planet11_game_view.screenRatioX;
import static com.example.game.planet11_game_view.screenRatioY;


public class planet11_game_start {

    int toShoot = 0;
    boolean isGoingUp = false;
    int x, y, width, height, apolakiCounter = 0, apolakiShootCounter = 1;
    Bitmap planet11_flight1, planet11_flight2, planet11_shoot1,planet11_shoot2,planet11_shoot3,planet11_shoot4,planet11_shoot5, dead;
    private planet11_game_view planet11GameView;


    planet11_game_start(planet11_game_view planet11GameView, int screenY, Resources res){

        this.planet11GameView = planet11GameView;

        planet11_flight1 = BitmapFactory.decodeResource(res, R.drawable.apolaki_small);
        planet11_flight2 = BitmapFactory.decodeResource(res, R.drawable.apolaki_with_aura);

        width = planet11_flight1.getWidth();
        height = planet11_flight1.getHeight();

        width /= 4;
        height /= 4;

        width *= (int) screenRatioX;
        height *= (int) screenRatioY;



        planet11_flight1 = Bitmap.createScaledBitmap(planet11_flight1, width, height, false);
        planet11_flight2 = Bitmap.createScaledBitmap(planet11_flight2, width, height, false);

        planet11_shoot1 = BitmapFactory.decodeResource(res, R.drawable.apolaki_with_aura);
        planet11_shoot2 = BitmapFactory.decodeResource(res, R.drawable.apolaki_with_aura);
        planet11_shoot3 = BitmapFactory.decodeResource(res, R.drawable.apolaki_with_aura);
        planet11_shoot4 = BitmapFactory.decodeResource(res, R.drawable.apolaki_with_aura);
        planet11_shoot5 = BitmapFactory.decodeResource(res, R.drawable.apolaki_with_aura);

        planet11_shoot1= Bitmap.createScaledBitmap(planet11_shoot1, width, height, false);
        planet11_shoot2= Bitmap.createScaledBitmap(planet11_shoot2, width, height, false);
        planet11_shoot3= Bitmap.createScaledBitmap(planet11_shoot3, width, height, false);
        planet11_shoot4= Bitmap.createScaledBitmap(planet11_shoot4, width, height, false);
        planet11_shoot5= Bitmap.createScaledBitmap(planet11_shoot5, width, height, false);



        dead = BitmapFactory.decodeResource(res , R.drawable.apolaki_small);
        dead = Bitmap.createScaledBitmap(dead, width, height, false);

        y = screenY / 2;
        x = (int) (64 * screenRatioX);
    }

    Bitmap getPlanet1_game_start() {

        if (toShoot != 0){

            if (apolakiShootCounter == 1){
                apolakiShootCounter++;
                return planet11_shoot1;
            }

            if (apolakiShootCounter == 2){
                apolakiShootCounter++;
                return planet11_shoot2;
            }

            if (apolakiShootCounter == 3){
                apolakiShootCounter++;
                return planet11_shoot3;
            }

            if (apolakiShootCounter == 4){
                apolakiShootCounter++;
                return planet11_shoot4;
            }

            apolakiShootCounter = 1;
            toShoot--;
            planet11GameView.newBullet();
            return planet11_shoot5;

        }

        if (apolakiCounter == 0 ){
            apolakiCounter ++;
            return planet11_flight1;
        }

        apolakiCounter--;
        return planet11_flight2;
    }


    Rect getCollisionShape () {
        return new Rect(x, y, x + width / 2, y + height / 3);
    }

    Bitmap getDead(){
        return dead;
    }
}
