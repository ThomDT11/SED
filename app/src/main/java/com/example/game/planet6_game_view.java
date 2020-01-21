package com.example.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.SurfaceView;

public class planet6_game_view extends SurfaceView implements Runnable{
    private Thread planet6_thread;
    private Boolean planet6_isPlaying;
    private int screenX, screenY;
    private Paint paint;
    private float screenRatioX, screenRatioY;
    private planet6_background planet6_background1, planet6_background2;

    public planet6_game_view(Context context, int screenX, int screenY) {
        super(context);

        this.screenX = screenX;
        this.screenY = screenY;

        screenRatioX = 3000f / screenX;
        screenRatioY = 1440f / screenY;

        planet6_background1 = new planet6_background(screenX, screenY, getResources());
        planet6_background2 = new planet6_background(screenX, screenY, getResources());

        planet6_background2.x = screenX;

        paint = new Paint();

    }

    @Override
    public void run() {

        while(planet6_isPlaying) {
            update();
            draw();
            sleep();
        }
    }

    private void update (){

        planet6_background1.x -= 10 * screenRatioX;
        planet6_background2.x -= 10 * screenRatioX;

        if(planet6_background1.x + planet6_background1.planet6_Background.getWidth() < 0 ) {
            planet6_background1.x = screenX;
        }

        if(planet6_background2.x + planet6_background2.planet6_Background.getWidth() < 0 ) {
            planet6_background2.x = screenX;
        }

    }

    private void draw () {

        if (getHolder().getSurface().isValid()){

            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(planet6_background1.planet6_Background, planet6_background1.x, planet6_background1.y, paint);
            canvas.drawBitmap(planet6_background2.planet6_Background, planet6_background2.x, planet6_background2.y, paint);

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

        planet6_isPlaying = true;
        planet6_thread = new Thread(this);
        planet6_thread.start();
    }

    public void pause () {

        try {
            planet6_isPlaying = false;
            planet6_thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}