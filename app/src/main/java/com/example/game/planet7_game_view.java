package com.example.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.SurfaceView;

public class planet7_game_view extends SurfaceView implements Runnable{

    private Thread planet7_thread;
    private Boolean planet7_isPlaying;
    private int screenX, screenY;
    private Paint paint;
    private float screenRatioX, screenRatioY;
    private planet7_background planet7_background1, planet7_background2;

    public planet7_game_view(Context context, int screenX, int screenY) {
        super(context);

        this.screenX = screenX;
        this.screenY = screenY;

        screenRatioX = 3000f / screenX;
        screenRatioY = 1440f / screenY;

        planet7_background1 = new planet7_background(screenX, screenY, getResources());
        planet7_background2 = new planet7_background(screenX, screenY, getResources());

        planet7_background2.x = screenX;

        paint = new Paint();

    }

    @Override
    public void run() {

        while(planet7_isPlaying) {
            update();
            draw();
            sleep();
        }
    }

    private void update (){

        planet7_background1.x -= 10 * screenRatioX;
        planet7_background2.x -= 10 * screenRatioX;

        if(planet7_background1.x + planet7_background1.planet7_Background.getWidth() < 0 ) {
            planet7_background1.x = screenX;
        }

        if(planet7_background2.x + planet7_background2.planet7_Background.getWidth() < 0 ) {
            planet7_background2.x = screenX;
        }

    }

    private void draw () {

        if (getHolder().getSurface().isValid()){

            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(planet7_background1.planet7_Background, planet7_background1.x, planet7_background1.y, paint);
            canvas.drawBitmap(planet7_background2.planet7_Background, planet7_background2.x, planet7_background2.y, paint);

            getHolder().unlockCanvasAndPost(canvas);

        }

    }

    private void sleep(){
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    public void resume () {

        planet7_isPlaying = true;
        planet7_thread = new Thread(this);
        planet7_thread.start();
    }

    public void pause () {

        try {
            planet7_isPlaying = false;
            planet7_thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
