package com.example.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class earth_game_view extends SurfaceView implements Runnable {

    private Thread thread;
    private Boolean isPlaying, isGameOver = false;
    private int screenX, screenY;
    private Paint paint;
    private earth_enemy[] earth_enemies;
    private Random random;
    private List<earth_bullet> earth_bullets;
    private earth_game_start earth_Game_start;
    public static float screenRatioX, screenRatioY;
    private earth_background background1, background2;

    public earth_game_view(Context context, int screenX, int screenY) {
        super(context);

        this.screenX = screenX;
        this.screenY = screenY;

        screenRatioX = 2560f / screenX;
        screenRatioY = 1440f / screenY;

        background1 = new earth_background(screenX, screenY, getResources());
        background2 = new earth_background(screenX, screenY, getResources());

        earth_Game_start = new earth_game_start(this, screenY, getResources());

        earth_bullets = new ArrayList<>();

        background2.x = screenX;

        paint = new Paint();

        earth_enemies = new earth_enemy[4];

        for(int i = 0; i < 4; i++){

            earth_enemy earth_Enemy = new earth_enemy(getResources());
            earth_enemies[i] = earth_Enemy;
        }

        random = new Random();

    }

    @Override
    public void run() {

        while (isPlaying) {

            update();
            draw();
            sleep();

        }

    }

    private void update() {

        background1.x -= 10 * screenRatioX;
        background2.x -= 10 * screenRatioX;

        if (background1.x + background1.earth_Background.getWidth() < 0) {
            background1.x = screenX;
        }

        if (background2.x + background2.earth_Background.getWidth() < 0) {
            background2.x = screenX;
        }

        if (earth_Game_start.isGoingUp)
            earth_Game_start.y -= 30 * screenRatioY;
        else
            earth_Game_start.y += 30 * screenRatioY;

        if (earth_Game_start.y < 0)
            earth_Game_start.y = 0;

        if (earth_Game_start.y > screenY - earth_Game_start.height)
            earth_Game_start.y = screenY - earth_Game_start.height;


        List<earth_bullet> earth_trash = new ArrayList<>();

        for (earth_bullet earth_Bullet : earth_bullets){

            if (earth_Bullet.x > screenX)
                earth_trash.add(earth_Bullet);

            earth_Bullet.x += 50 * screenRatioX;

            for (earth_enemy earth_enemy : earth_enemies){

                if(Rect.intersects(earth_enemy.getCollisionShape(), earth_Bullet.getCollisionShape())){

                    earth_enemy.x = -500;
                    earth_Bullet.x = screenX + 500;
                    earth_enemy.wasShot = true;
                }

            }
        }

        for (earth_bullet earth_Bullet : earth_trash)
                earth_bullets.remove(earth_Bullet);

        for (earth_enemy earth_enemy : earth_enemies){

            earth_enemy.x -= earth_enemy.speed;

            if (earth_enemy.x + earth_enemy.width < 0){

                if(!earth_enemy.wasShot){
                    isGameOver = true;
                    return;
                }

                int bound = (int) (30 * screenRatioX);
                earth_enemy.speed = random.nextInt(bound);

                if (earth_enemy.speed < 10 * screenRatioX)
                    earth_enemy.speed = (int) (10 * screenRatioX);

                earth_enemy.x = screenX;
                earth_enemy.y = random.nextInt(screenY - earth_enemy.height);

                earth_enemy.wasShot = false;

                if (Rect.intersects(earth_enemy.getCollisionShape(), earth_Game_start.getCollisionShape())){

                       isGameOver = true;
                       return;
                }

            }

        }

    }



    private void draw () {

        if (getHolder().getSurface().isValid()){

            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.earth_Background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.earth_Background, background2.x, background2.y, paint);

            if (isGameOver){
                isPlaying = false;
                canvas.drawBitmap(earth_Game_start.getDead(), earth_Game_start.x, earth_Game_start.y, paint);
                getHolder().unlockCanvasAndPost(canvas);
                return;
            }

            for (earth_enemy earth_enemy : earth_enemies)
                canvas.drawBitmap(earth_enemy.getearth_enemy(), earth_enemy.x, earth_enemy.y, paint);

            canvas.drawBitmap(earth_Game_start.getPlanet1_game_start(), earth_Game_start.x,earth_Game_start.y, paint);

            for(earth_bullet earthBullet : earth_bullets)
                canvas.drawBitmap(earthBullet.earth_Bullet, earthBullet.x, earthBullet.y, paint);

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

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (event.getX() < screenX / 2){
                    earth_Game_start.isGoingUp = true;
                }
            break;
            case  MotionEvent.ACTION_UP:
                    earth_Game_start.isGoingUp= false;
                    if (event.getX() > screenX / 2)
                        earth_Game_start.toShoot++;
            break;
        }

        return true;
    }

    public void newBullet() {

        earth_bullet Earth_Bullet = new earth_bullet(getResources());
        Earth_Bullet.x = earth_Game_start.x + earth_Game_start.width;
        Earth_Bullet.y = earth_Game_start.y + earth_Game_start.height;

        earth_bullets.add(Earth_Bullet);

    }
}




