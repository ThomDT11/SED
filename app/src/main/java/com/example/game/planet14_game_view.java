package com.example.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceView;

public class planet14_game_view extends SurfaceView implements Runnable{


    private Thread planet14_thread;
    private Boolean planet14_isPlaying;
    private int screenX, screenY;
    private Paint paint;
    private float screenRatioX, screenRatioY;
    private planet14_background planet14_background1, planet14_background2;

    public planet14_game_view(Context context, int screenX, int screenY) {
        super(context);

        this.screenX = screenX;
        this.screenY = screenY;

        screenRatioX = 3000f / screenX;
        screenRatioY = 1440f / screenY;

        planet14_background1 = new planet14_background(screenX, screenY, getResources());
        planet14_background2 = new planet14_background(screenX, screenY, getResources());

        planet14_background2.x = screenX;

        paint = new Paint();

    }

    @Override
    public void run() {

        while(planet14_isPlaying) {
            update();
            draw();
            sleep();
        }
    }

    private void update (){

        planet14_background1.x -= 10 * screenRatioX;
        planet14_background2.x -= 10 * screenRatioX;

        if(planet14_background1.x + planet14_background1.planet14_Background.getWidth() < 0 ) {
            planet14_background1.x = screenX;
        }

        if(planet14_background2.x + planet14_background2.planet14_Background.getWidth() < 0 ) {
            planet14_background2.x = screenX;
        }

    }

    private void draw () {

        if (getHolder().getSurface().isValid()){

            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(planet14_background1.planet14_Background, planet14_background1.x, planet14_background1.y, paint);
            canvas.drawBitmap(planet14_background2.planet14_Background, planet14_background2.x, planet14_background2.y, paint);

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

        planet14_isPlaying = true;
        planet14_thread = new Thread(this);
        planet14_thread.start();
    }

    public void pause () {

        try {
            planet14_isPlaying = false;
            planet14_thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
