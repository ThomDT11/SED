package com.example.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceView;

public class planet13_game_view extends SurfaceView implements Runnable {

    private Thread planet13_thread;
    private Boolean planet13_isPlaying;
    private int screenX, screenY;
    private Paint paint;
    private float screenRatioX, screenRatioY;
    private planet13_background planet13_background1, planet13_background2;

    public planet13_game_view(Context context, int screenX, int screenY) {
        super(context);

        this.screenX = screenX;
        this.screenY = screenY;

        screenRatioX = 3000f / screenX;
        screenRatioY = 1440f / screenY;

        planet13_background1 = new planet13_background(screenX, screenY, getResources());
        planet13_background2 = new planet13_background(screenX, screenY, getResources());

        planet13_background2.x = screenX;

        paint = new Paint();

    }

    @Override
    public void run() {

        while(planet13_isPlaying) {
            update();
            draw();
            sleep();
        }
    }

    private void update (){

        planet13_background1.x -= 10 * screenRatioX;
        planet13_background2.x -= 10 * screenRatioX;

        if(planet13_background1.x + planet13_background1.planet13_Background.getWidth() < 0 ) {
            planet13_background1.x = screenX;
        }

        if(planet13_background2.x + planet13_background2.planet13_Background.getWidth() < 0 ) {
            planet13_background2.x = screenX;
        }

    }

    private void draw () {

        if (getHolder().getSurface().isValid()){

            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(planet13_background1.planet13_Background, planet13_background1.x, planet13_background1.y, paint);
            canvas.drawBitmap(planet13_background2.planet13_Background, planet13_background2.x, planet13_background2.y, paint);

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

        planet13_isPlaying = true;
        planet13_thread = new Thread(this);
        planet13_thread.start();
    }

    public void pause () {

        try {
            planet13_isPlaying = false;
            planet13_thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
