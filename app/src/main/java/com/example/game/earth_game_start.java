package com.example.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.game.earth_game_view.screenRatioX;
import static com.example.game.earth_game_view.screenRatioY;


public class earth_game_start {

    int toShoot = 0;
    boolean isGoingUp = false;
    int x, y, width, height, apolakiCounter = 0, apolakiShootCounter = 1;
    Bitmap earth_flight1, earth_flight2, earth_shoot1,earth_shoot2,earth_shoot3,earth_shoot4,earth_shoot5, dead;
    private earth_game_view earthGameView;


    earth_game_start(earth_game_view earthGameView, int screenY, Resources res){

        this.earthGameView = earthGameView;

        earth_flight1 = BitmapFactory.decodeResource(res, R.drawable.apolaki_small);
        earth_flight2 = BitmapFactory.decodeResource(res, R.drawable.apolaki_with_aura);

        width = earth_flight1.getWidth();
        height = earth_flight1.getHeight();

        width /= 4;
        height /= 4;

        width *= (int) screenRatioX;
        height *= (int) screenRatioY;



        earth_flight1 = Bitmap.createScaledBitmap(earth_flight1, width, height, false);
        earth_flight2 = Bitmap.createScaledBitmap(earth_flight2, width, height, false);

        earth_shoot1 = BitmapFactory.decodeResource(res, R.drawable.apolaki_with_aura);
        earth_shoot2 = BitmapFactory.decodeResource(res, R.drawable.apolaki_with_aura);
        earth_shoot3 = BitmapFactory.decodeResource(res, R.drawable.apolaki_with_aura);
        earth_shoot4 = BitmapFactory.decodeResource(res, R.drawable.apolaki_with_aura);
        earth_shoot5 = BitmapFactory.decodeResource(res, R.drawable.apolaki_with_aura);

        earth_shoot1= Bitmap.createScaledBitmap(earth_shoot1, width, height, false);
        earth_shoot2= Bitmap.createScaledBitmap(earth_shoot2, width, height, false);
        earth_shoot3= Bitmap.createScaledBitmap(earth_shoot3, width, height, false);
        earth_shoot4= Bitmap.createScaledBitmap(earth_shoot4, width, height, false);
        earth_shoot5= Bitmap.createScaledBitmap(earth_shoot5, width, height, false);



        dead = BitmapFactory.decodeResource(res , R.drawable.apolaki_small);
        dead = Bitmap.createScaledBitmap(dead, width, height, false);

        y = screenY / 2;
        x = (int) (64 * screenRatioX);
    }

    Bitmap getPlanet1_game_start() {

        if (toShoot != 0){

            if (apolakiShootCounter == 1){
                apolakiShootCounter++;
                return earth_shoot1;
            }

            if (apolakiShootCounter == 2){
                apolakiShootCounter++;
                return earth_shoot2;
            }

            if (apolakiShootCounter == 3){
                apolakiShootCounter++;
                return earth_shoot3;
            }

            if (apolakiShootCounter == 4){
                apolakiShootCounter++;
                return earth_shoot4;
            }

            apolakiShootCounter = 1;
            toShoot--;
            earthGameView.newBullet();
            return earth_shoot5;

        }

        if (apolakiCounter == 0 ){
            apolakiCounter ++;
            return earth_flight1;
        }

        apolakiCounter--;
        return earth_flight2;
    }


    Rect getCollisionShape () {
        return new Rect(x, y, x + width / 2, y + height / 2);
    }

    Bitmap getDead(){
        return dead;
    }
}
