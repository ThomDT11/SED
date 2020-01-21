package com.example.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceView;

public class planet9_game_view extends SurfaceView implements Runnable{

    private Thread planet9_thread;
    private Boolean planet9_isPlaying;
    private int screenX, screenY;
    private Paint paint;
    private float screenRatioX, screenRatioY;
    private planet9_background planet9_background1, planet9_background2;

    public planet9_game_view(Context context, int screenX, int screenY) {
        super(context);

        this.screenX = screenX;
        this.screenY = screenY;

        screenRatioX = 3000f / screenX;
        screenRatioY = 1440f / screenY;

        planet9_background1 = new planet9_background(screenX, screenY, getResources());
        planet9_background2 = new planet9_background(screenX, screenY, getResources());

        planet9_background2.x = screenX;

        paint = new Paint();

    }

    @Override
    public void run() {

        while(planet9_isPlaying) {
            update();
            draw();
            sleep();
        }
    }

    private void update (){

        planet9_background1.x -= 10 * screenRatioX;
        planet9_background2.x -= 10 * screenRatioX;

        if(planet9_background1.x + planet9_background1.planet9_Background.getWidth() < 0 ) {
            planet9_background1.x = screenX;
        }

        if(planet9_background2.x + planet9_background2.planet9_Background.getWidth() < 0 ) {
            planet9_background2.x = screenX;
        }

    }

    private void draw () {

        if (getHolder().getSurface().isValid()){

            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(planet9_background1.planet9_Background, planet9_background1.x, planet9_background1.y, paint);
            canvas.drawBitmap(planet9_background2.planet9_Background, planet9_background2.x, planet9_background2.y, paint);

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

        planet9_isPlaying = true;
        planet9_thread = new Thread(this);
        planet9_thread.start();
    }

    public void pause () {

        try {
            planet9_isPlaying = false;
            planet9_thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
