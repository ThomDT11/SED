package com.example.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.SurfaceView;

public class planet8_game_view extends SurfaceView implements Runnable{

    private Thread planet8_thread;
    private Boolean planet8_isPlaying;
    private int screenX, screenY;
    private Paint paint;
    private float screenRatioX, screenRatioY;
    private planet8_background planet8_background1, planet8_background2;

    public planet8_game_view(Context context, int screenX, int screenY) {
        super(context);

        this.screenX = screenX;
        this.screenY = screenY;

        screenRatioX = 3000f / screenX;
        screenRatioY = 1440f / screenY;

        planet8_background1 = new planet8_background(screenX, screenY, getResources());
        planet8_background2 = new planet8_background(screenX, screenY, getResources());

        planet8_background2.x = screenX;

        paint = new Paint();

    }

    @Override
    public void run() {

        while(planet8_isPlaying) {
            update();
            draw();
            sleep();
        }
    }

    private void update (){

        planet8_background1.x -= 10 * screenRatioX;
        planet8_background2.x -= 10 * screenRatioX;

        if(planet8_background1.x + planet8_background1.planet8_Background.getWidth() < 0 ) {
            planet8_background1.x = screenX;
        }

        if(planet8_background2.x + planet8_background2.planet8_Background.getWidth() < 0 ) {
            planet8_background2.x = screenX;
        }

    }

    private void draw () {

        if (getHolder().getSurface().isValid()){

            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(planet8_background1.planet8_Background, planet8_background1.x, planet8_background1.y, paint);
            canvas.drawBitmap(planet8_background2.planet8_Background, planet8_background2.x, planet8_background2.y, paint);

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

        planet8_isPlaying = true;
        planet8_thread = new Thread(this);
        planet8_thread.start();
    }

    public void pause () {

        try {
            planet8_isPlaying = false;
            planet8_thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
