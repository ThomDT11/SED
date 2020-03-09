package com.example.game;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class planet5_game_view extends SurfaceView implements Runnable {


    private Thread thread;
    private Boolean isPlaying, isGameOver = false;
    private int screenX, screenY, kill = 0;
    private Paint paint;
    private planet5_enemy[] planet5_enemies;
    private Random random;
    private SharedPreferences prefs;
    private List<planet5_bullet> planet5_bullets;
    private planet5_game_start planet5_Game_start;
    public static float screenRatioX, screenRatioY;
    private planet5_game_activity activity;
    private planet5_background background1, background2;

    public planet5_game_view(planet5_game_activity activity, int screenX, int screenY) {
        super(activity);

        this.activity = activity;

        prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE);


        this.screenX = screenX;
        this.screenY = screenY;

        screenRatioX = 2560f / screenX;
        screenRatioY = 1440f / screenY;

        background1 = new planet5_background(screenX, screenY, getResources());
        background2 = new planet5_background(screenX, screenY, getResources());

        planet5_Game_start = new planet5_game_start(this, screenY, getResources());

        planet5_bullets = new ArrayList<>();

        background2.x = screenX;

        paint = new Paint();
        paint.setTextSize(128);
        paint.setColor(Color.WHITE);

        planet5_enemies = new planet5_enemy[4];

        for(int i = 0; i < 4; i++){

            planet5_enemy planet5_Enemy = new planet5_enemy(getResources());
            planet5_enemies[i] = planet5_Enemy;
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

        if (background1.x + background1.planet5_Background.getWidth() < 0) {
            background1.x = screenX;
        }

        if (background2.x + background2.planet5_Background.getWidth() < 0) {
            background2.x = screenX;
        }

        if (planet5_Game_start.isGoingUp)
            planet5_Game_start.y -= 30 * screenRatioY;
        else
            planet5_Game_start.y += 30 * screenRatioY;

        if (planet5_Game_start.y < 0)
            planet5_Game_start.y = 0;

        if (planet5_Game_start.y > screenY - planet5_Game_start.height)
            planet5_Game_start.y = screenY - planet5_Game_start.height;


        List<planet5_bullet> planet5_trash = new ArrayList<>();

        for (planet5_bullet planet5_Bullet : planet5_bullets){

            if (planet5_Bullet.x > screenX)
                planet5_trash.add(planet5_Bullet);

            planet5_Bullet.x += 50 * screenRatioX;

            for (planet5_enemy planet5_enemy : planet5_enemies){

                if(Rect.intersects(planet5_enemy.getCollisionShape(), planet5_Bullet.getCollisionShape())){

                    kill++;
                    planet5_enemy.x = -500;
                    planet5_Bullet.x = screenX + 500;
                    planet5_enemy.wasShot = true;
                }

            }
        }

        for (planet5_bullet planet5_Bullet : planet5_trash)
            planet5_bullets.remove(planet5_Bullet);

        for (planet5_enemy planet5_enemy : planet5_enemies){

            planet5_enemy.x -= planet5_enemy.speed;

            if (planet5_enemy.x + planet5_enemy.width < 0){

                if(!planet5_enemy.wasShot){
                    isGameOver = true;
                    return;
                }



                int bound = (int) (30 * screenRatioX);
                planet5_enemy.speed = random.nextInt(bound);

                if (planet5_enemy.speed < 10 * screenRatioX)
                    planet5_enemy.speed = (int) (10 * screenRatioX);

                planet5_enemy.x = screenX;
                planet5_enemy.y = random.nextInt(screenY - planet5_enemy.height);

                planet5_enemy.wasShot = false;


            }
            if (Rect.intersects(planet5_Game_start.getCollisionShape(), planet5_enemy.getCollisionShape())){
                isGameOver = true;
                return;}

        }

    }





    private void draw () {

        if (getHolder().getSurface().isValid()){

            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.planet5_Background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.planet5_Background, background2.x, background2.y, paint);

            for (planet5_enemy planet5_enemy : planet5_enemies)
                canvas.drawBitmap(planet5_enemy.getplanet5_enemy(), planet5_enemy.x, planet5_enemy.y, paint);

            canvas.drawText(kill + "", screenX / 2f, 164,  paint);

            if (isGameOver){
                isPlaying = false;
                canvas.drawBitmap(planet5_Game_start.getDead(), planet5_Game_start.x, planet5_Game_start.y, paint);
                saveIfDone();
                waitBeforeExiting();
                getHolder().unlockCanvasAndPost(canvas);
                return ;
            }


            canvas.drawBitmap(planet5_Game_start.getPlanet1_game_start(), planet5_Game_start.x,planet5_Game_start.y, paint);

            for(planet5_bullet planet5Bullet : planet5_bullets)
                canvas.drawBitmap(planet5Bullet.planet5_Bullet, planet5Bullet.x, planet5Bullet.y, paint);

            getHolder().unlockCanvasAndPost(canvas);

        }

    }

    private void saveIfDone() {

        if (prefs.getInt("highscore",  0 ) < kill){
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt ("highscore", kill);
            editor.apply();

        }

    }

    private void waitBeforeExiting(){

        try {
            Thread.sleep(2000);
            activity.startActivity(new Intent(activity, planet5_game.class));
            activity.finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
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
                    planet5_Game_start.isGoingUp = true;
                }
                break;
            case  MotionEvent.ACTION_UP:
                planet5_Game_start.isGoingUp= false;

                if (event.getX() > screenX / 2)
                    planet5_Game_start.toShoot++;
                break;

        }


        return true;
    }

    public void newBullet() {

        planet5_bullet planet5_Bullet = new planet5_bullet(getResources());
        planet5_Bullet.x = planet5_Game_start.x + planet5_Game_start.width;
        planet5_Bullet.y = planet5_Game_start.y + (planet5_Game_start.height/3) ;

        planet5_bullets.add(planet5_Bullet);

    }
}

