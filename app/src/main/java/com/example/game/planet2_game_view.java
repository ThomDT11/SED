package com.example.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceView;

public class planet2_game_view extends SurfaceView implements Runnable {

    private Thread planet2_thread;
    private Boolean planet2_isPlaying;
    private int screenX, screenY;
    private Paint paint;
    private float screenRatioX, screenRatioY;
    private planet2_background planet2_background1, planet2_background2;

    public planet2_game_view(Context context, int screenX, int screenY) {
        super(context);

        this.screenX = screenX;
        this.screenY = screenY;

        screenRatioX = 2560f / screenX;
        screenRatioY = 1440f / screenY;

        planet2_background1 = new planet2_background(screenX, screenY, getResources());
        planet2_background2 = new planet2_background(screenX, screenY, getResources());

        planet2_background2.x = screenX;

        paint = new Paint();

    }

    @Override
    public void run() {

        while(planet2_isPlaying) {
            update();
            draw();
            sleep();
        }
    }

    private void update (){

        planet2_background1.x -= 10 * screenRatioX;
        planet2_background2.x -= 10 * screenRatioX;

        if(planet2_background1.x + planet2_background1.planet2_Background.getWidth() < 0 ) {
            planet2_background1.x = screenX;
        }

        if(planet2_background2.x + planet2_background2.planet2_Background.getWidth() < 0 ) {
            planet2_background2.x = screenX;
        }

    }

    private void draw () {

        if (getHolder().getSurface().isValid()){

            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(planet2_background1.planet2_Background, planet2_background1.x, planet2_background1.y, paint);
            canvas.drawBitmap(planet2_background2.planet2_Background, planet2_background2.x, planet2_background2.y, paint);

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

        planet2_isPlaying = true;
        planet2_thread = new Thread(this);
        planet2_thread.start();
    }

    public void pause () {

        try {
            planet2_isPlaying = false;
            planet2_thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
