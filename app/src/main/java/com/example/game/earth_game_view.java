package com.example.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceView;

public class earth_game_view extends SurfaceView implements Runnable{

    private Thread thread;
    private Boolean isPlaying;
    private int screenX, screenY;
    private Paint paint;
    private float screenRatioX, screenRatioY;
    private earth_background background1, background2;

    public earth_game_view(Context context, int screenX, int screenY) {
        super(context);

        this.screenX = screenX;
        this.screenY = screenY;

        screenRatioX = 2560f / screenX;
        screenRatioY = 1440f / screenY;

        background1 = new earth_background(screenX, screenY, getResources());
        background2 = new earth_background(screenX, screenY, getResources());

        background2.x = screenX;

        paint = new Paint();

    }

    @Override
    public void run() {

        while (isPlaying){

            update ();
            draw ();
            sleep ();

        }

    }

    private void update (){

        background1.x -= 10 * screenRatioX;
        background2.x -= 10 * screenRatioX;

        if(background1.x + background1.earth_Background.getWidth() < 0 ) {
            background1.x = screenX;
        }

        if(background2.x + background2.earth_Background.getWidth() < 0 ) {
            background2.x = screenX;
        }

    }

    private void draw () {

        if (getHolder().getSurface().isValid()){

            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.earth_Background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.earth_Background, background2.x, background2.y, paint);

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

        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }

    public void pause () {

        try {
            isPlaying = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


}




