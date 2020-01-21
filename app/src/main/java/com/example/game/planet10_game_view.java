package com.example.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceView;

public class planet10_game_view extends SurfaceView implements Runnable{

    private Thread planet10_thread;
    private Boolean planet10_isPlaying;
    private int screenX, screenY;
    private Paint paint;
    private float screenRatioX, screenRatioY;
    private planet10_background planet10_background1, planet10_background2;

    public planet10_game_view(Context context, int screenX, int screenY) {
        super(context);

        this.screenX = screenX;
        this.screenY = screenY;

        screenRatioX = 3000f / screenX;
        screenRatioY = 1440f / screenY;

        planet10_background1 = new planet10_background(screenX, screenY, getResources());
        planet10_background2 = new planet10_background(screenX, screenY, getResources());

        planet10_background2.x = screenX;

        paint = new Paint();

    }

    @Override
    public void run() {

        while(planet10_isPlaying) {
            update();
            draw();
            sleep();
        }
    }

    private void update (){

        planet10_background1.x -= 10 * screenRatioX;
        planet10_background2.x -= 10 * screenRatioX;

        if(planet10_background1.x + planet10_background1.planet10_Background.getWidth() < 0 ) {
            planet10_background1.x = screenX;
        }

        if(planet10_background2.x + planet10_background2.planet10_Background.getWidth() < 0 ) {
            planet10_background2.x = screenX;
        }

    }

    private void draw () {

        if (getHolder().getSurface().isValid()){

            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(planet10_background1.planet10_Background, planet10_background1.x, planet10_background1.y, paint);
            canvas.drawBitmap(planet10_background2.planet10_Background, planet10_background2.x, planet10_background2.y, paint);

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

        planet10_isPlaying = true;
        planet10_thread = new Thread(this);
        planet10_thread.start();
    }

    public void pause () {

        try {
            planet10_isPlaying = false;
            planet10_thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

