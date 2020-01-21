package com.example.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceView;

public class planet5_game_view extends SurfaceView implements Runnable {
    private Thread planet5_thread;
    private Boolean planet5_isPlaying;
    private int screenX, screenY;
    private Paint paint;
    private float screenRatioX, screenRatioY;
    private planet5_background planet5_background1, planet5_background2;

    public planet5_game_view(Context context, int screenX, int screenY) {
        super(context);

        this.screenX = screenX;
        this.screenY = screenY;

        screenRatioX = 3000f / screenX;
        screenRatioY = 1440f / screenY;

        planet5_background1 = new planet5_background(screenX, screenY, getResources());
        planet5_background2 = new planet5_background(screenX, screenY, getResources());

        planet5_background2.x = screenX;

        paint = new Paint();

    }

    @Override
    public void run() {

        while(planet5_isPlaying) {
            update();
            draw();
            sleep();
        }
    }

    private void update (){

        planet5_background1.x -= 10 * screenRatioX;
        planet5_background2.x -= 10 * screenRatioX;

        if(planet5_background1.x + planet5_background1.planet5_Background.getWidth() < 0 ) {
            planet5_background1.x = screenX;
        }

        if(planet5_background2.x + planet5_background2.planet5_Background.getWidth() < 0 ) {
            planet5_background2.x = screenX;
        }

    }

    private void draw () {

        if (getHolder().getSurface().isValid()){

            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(planet5_background1.planet5_Background, planet5_background1.x, planet5_background1.y, paint);
            canvas.drawBitmap(planet5_background2.planet5_Background, planet5_background2.x, planet5_background2.y, paint);

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

        planet5_isPlaying = true;
        planet5_thread = new Thread(this);
        planet5_thread.start();
    }

    public void pause () {

        try {
            planet5_isPlaying = false;
            planet5_thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
