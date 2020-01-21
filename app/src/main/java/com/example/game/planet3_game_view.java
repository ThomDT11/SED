package com.example.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceView;

public class planet3_game_view extends SurfaceView implements Runnable {


    private Thread planet3_thread;
    private Boolean planet3_isPlaying;
    private int screenX, screenY;
    private Paint paint;
    private float screenRatioX, screenRatioY;
    private planet3_background planet3_background1, planet3_background2;

    public planet3_game_view(Context context, int screenX, int screenY) {
        super(context);

        this.screenX = screenX;
        this.screenY = screenY;

        screenRatioX = 3000f / screenX;
        screenRatioY = 1440f / screenY;

        planet3_background1 = new planet3_background(screenX, screenY, getResources());
        planet3_background2 = new planet3_background(screenX, screenY, getResources());

        planet3_background2.x = screenX;

        paint = new Paint();

    }

    @Override
    public void run() {

        while(planet3_isPlaying) {
            update();
            draw();
            sleep();
        }
    }

    private void update (){

        planet3_background1.x -= 10 * screenRatioX;
        planet3_background2.x -= 10 * screenRatioX;

        if(planet3_background1.x + planet3_background1.planet3_Background.getWidth() < 0 ) {
            planet3_background1.x = screenX;
        }

        if(planet3_background2.x + planet3_background2.planet3_Background.getWidth() < 0 ) {
            planet3_background2.x = screenX;
        }

    }

    private void draw () {

        if (getHolder().getSurface().isValid()){

            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(planet3_background1.planet3_Background, planet3_background1.x, planet3_background1.y, paint);
            canvas.drawBitmap(planet3_background2.planet3_Background, planet3_background2.x, planet3_background2.y, paint);

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

        planet3_isPlaying = true;
        planet3_thread = new Thread(this);
        planet3_thread.start();
    }

    public void pause () {

        try {
            planet3_isPlaying = false;
            planet3_thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
