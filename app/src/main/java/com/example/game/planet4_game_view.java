package com.example.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceView;

public class planet4_game_view extends SurfaceView implements Runnable {
    private Thread planet4_thread;
    private Boolean planet4_isPlaying;
    private int screenX, screenY;
    private Paint paint;
    private float screenRatioX, screenRatioY;
    private planet4_background planet4_background1, planet4_background2;

    public planet4_game_view(Context context, int screenX, int screenY) {
        super(context);

        this.screenX = screenX;
        this.screenY = screenY;

        screenRatioX = 3000f / screenX;
        screenRatioY = 1440f / screenY;

        planet4_background1 = new planet4_background(screenX, screenY, getResources());
        planet4_background2 = new planet4_background(screenX, screenY, getResources());

        planet4_background2.x = screenX;

        paint = new Paint();

    }

    @Override
    public void run() {

        while(planet4_isPlaying) {
            update();
            draw();
            sleep();
        }
    }

    private void update (){

        planet4_background1.x -= 10 * screenRatioX;
        planet4_background2.x -= 10 * screenRatioX;

        if(planet4_background1.x + planet4_background1.planet4_Background.getWidth() < 0 ) {
            planet4_background1.x = screenX;
        }

        if(planet4_background2.x + planet4_background2.planet4_Background.getWidth() < 0 ) {
            planet4_background2.x = screenX;
        }

    }

    private void draw () {

        if (getHolder().getSurface().isValid()){

            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(planet4_background1.planet4_Background, planet4_background1.x, planet4_background1.y, paint);
            canvas.drawBitmap(planet4_background2.planet4_Background, planet4_background2.x, planet4_background2.y, paint);

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

        planet4_isPlaying = true;
        planet4_thread = new Thread(this);
        planet4_thread.start();
    }

    public void pause () {

        try {
            planet4_isPlaying = false;
            planet4_thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
