package com.example.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceView;

public class planet15_game_view extends SurfaceView implements Runnable{

    private Thread planet15_thread;
    private Boolean planet15_isPlaying;
    private int screenX, screenY;
    private Paint paint;
    private float screenRatioX, screenRatioY;
    private planet15_background planet15_background1, planet15_background2;

    public planet15_game_view(Context context, int screenX, int screenY) {
        super(context);

        this.screenX = screenX;
        this.screenY = screenY;

        screenRatioX = 3000f / screenX;
        screenRatioY = 1440f / screenY;

        planet15_background1 = new planet15_background(screenX, screenY, getResources());
        planet15_background2 = new planet15_background(screenX, screenY, getResources());

        planet15_background2.x = screenX;

        paint = new Paint();

    }

    @Override
    public void run() {

        while(planet15_isPlaying) {
            update();
            draw();
            sleep();
        }
    }

    private void update (){

        planet15_background1.x -= 10 * screenRatioX;
        planet15_background2.x -= 10 * screenRatioX;

        if(planet15_background1.x + planet15_background1.planet15_Background.getWidth() < 0 ) {
            planet15_background1.x = screenX;
        }

        if(planet15_background2.x + planet15_background2.planet15_Background.getWidth() < 0 ) {
            planet15_background2.x = screenX;
        }

    }

    private void draw () {

        if (getHolder().getSurface().isValid()){

            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(planet15_background1.planet15_Background, planet15_background1.x, planet15_background1.y, paint);
            canvas.drawBitmap(planet15_background2.planet15_Background, planet15_background2.x, planet15_background2.y, paint);

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

        planet15_isPlaying = true;
        planet15_thread = new Thread(this);
        planet15_thread.start();
    }

    public void pause () {

        try {
            planet15_isPlaying = false;
            planet15_thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
