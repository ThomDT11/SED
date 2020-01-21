package com.example.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceView;

public class planet12_game_view extends SurfaceView implements Runnable{


    private Thread planet12_thread;
    private Boolean planet12_isPlaying;
    private int screenX, screenY;
    private Paint paint;
    private float screenRatioX, screenRatioY;
    private planet12_background planet12_background1, planet12_background2;

    public planet12_game_view(Context context, int screenX, int screenY) {
        super(context);

        this.screenX = screenX;
        this.screenY = screenY;

        screenRatioX = 3000f / screenX;
        screenRatioY = 1440f / screenY;

        planet12_background1 = new planet12_background(screenX, screenY, getResources());
        planet12_background2 = new planet12_background(screenX, screenY, getResources());

        planet12_background2.x = screenX;

        paint = new Paint();

    }

    @Override
    public void run() {

        while(planet12_isPlaying) {
            update();
            draw();
            sleep();
        }
    }

    private void update (){

        planet12_background1.x -= 10 * screenRatioX;
        planet12_background2.x -= 10 * screenRatioX;

        if(planet12_background1.x + planet12_background1.planet12_Background.getWidth() < 0 ) {
            planet12_background1.x = screenX;
        }

        if(planet12_background2.x + planet12_background2.planet12_Background.getWidth() < 0 ) {
            planet12_background2.x = screenX;
        }

    }

    private void draw () {

        if (getHolder().getSurface().isValid()){

            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(planet12_background1.planet12_Background, planet12_background1.x, planet12_background1.y, paint);
            canvas.drawBitmap(planet12_background2.planet12_Background, planet12_background2.x, planet12_background2.y, paint);

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

        planet12_isPlaying = true;
        planet12_thread = new Thread(this);
        planet12_thread.start();
    }

    public void pause () {

        try {
            planet12_isPlaying = false;
            planet12_thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
