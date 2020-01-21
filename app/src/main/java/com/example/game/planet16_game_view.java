package com.example.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceView;

public class planet16_game_view extends SurfaceView implements Runnable{

    private Thread planet16_thread;
    private Boolean planet16_isPlaying;
    private int screenX, screenY;
    private Paint paint;
    private float screenRatioX, screenRatioY;
    private planet16_background planet16_background1, planet16_background2;

    public planet16_game_view(Context context, int screenX, int screenY) {
        super(context);

        this.screenX = screenX;
        this.screenY = screenY;

        screenRatioX = 3000f / screenX;
        screenRatioY = 1440f / screenY;

        planet16_background1 = new planet16_background(screenX, screenY, getResources());
        planet16_background2 = new planet16_background(screenX, screenY, getResources());

        planet16_background2.x = screenX;

        paint = new Paint();

    }

    @Override
    public void run() {

        while(planet16_isPlaying) {
            update();
            draw();
            sleep();
        }
    }

    private void update (){

        planet16_background1.x -= 10 * screenRatioX;
        planet16_background2.x -= 10 * screenRatioX;

        if(planet16_background1.x + planet16_background1.planet16_Background.getWidth() < 0 ) {
            planet16_background1.x = screenX;
        }

        if(planet16_background2.x + planet16_background2.planet16_Background.getWidth() < 0 ) {
            planet16_background2.x = screenX;
        }

    }

    private void draw () {

        if (getHolder().getSurface().isValid()){

            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(planet16_background1.planet16_Background, planet16_background1.x, planet16_background1.y, paint);
            canvas.drawBitmap(planet16_background2.planet16_Background, planet16_background2.x, planet16_background2.y, paint);

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

        planet16_isPlaying = true;
        planet16_thread = new Thread(this);
        planet16_thread.start();
    }

    public void pause () {

        try {
            planet16_isPlaying = false;
            planet16_thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

