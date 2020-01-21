package com.example.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceView;

public class planet11_game_view extends SurfaceView implements Runnable {


    private Thread planet11_thread;
    private Boolean planet11_isPlaying;
    private int screenX, screenY;
    private Paint paint;
    private float screenRatioX, screenRatioY;
    private planet11_background planet11_background1, planet11_background2;

    public planet11_game_view(Context context, int screenX, int screenY) {
        super(context);

        this.screenX = screenX;
        this.screenY = screenY;

        screenRatioX = 3000f / screenX;
        screenRatioY = 1440f / screenY;

        planet11_background1 = new planet11_background(screenX, screenY, getResources());
        planet11_background2 = new planet11_background(screenX, screenY, getResources());

        planet11_background2.x = screenX;

        paint = new Paint();

    }

    @Override
    public void run() {

        while(planet11_isPlaying) {
            update();
            draw();
            sleep();
        }
    }

    private void update (){

        planet11_background1.x -= 10 * screenRatioX;
        planet11_background2.x -= 10 * screenRatioX;

        if(planet11_background1.x + planet11_background1.planet11_Background.getWidth() < 0 ) {
            planet11_background1.x = screenX;
        }

        if(planet11_background2.x + planet11_background2.planet11_Background.getWidth() < 0 ) {
            planet11_background2.x = screenX;
        }

    }

    private void draw () {

        if (getHolder().getSurface().isValid()){

            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(planet11_background1.planet11_Background, planet11_background1.x, planet11_background1.y, paint);
            canvas.drawBitmap(planet11_background2.planet11_Background, planet11_background2.x, planet11_background2.y, paint);

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

        planet11_isPlaying = true;
        planet11_thread = new Thread(this);
        planet11_thread.start();
    }

    public void pause () {

        try {
            planet11_isPlaying = false;
            planet11_thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
