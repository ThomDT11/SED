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

public class planet13_game_view extends SurfaceView implements Runnable {


    private Thread thread;
    private Boolean isPlaying, isGameOver = false, stageFinished = false;
    private int screenX, screenY, kill = 0, life = 5;
    private Paint paint;
    private planet13_enemy[] planet13_enemies;
    private Random random;
    private SharedPreferences prefs;
    private List<planet13_bullet> planet13_bullets;
    private planet13_game_start planet13_Game_start;
    public static float screenRatioX, screenRatioY;
    private planet13_game_activity activity;
    private planet13_background background1, background2;

    public planet13_game_view(planet13_game_activity activity, int screenX, int screenY) {
        super(activity);

        this.activity = activity;

        prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE);


        this.screenX = screenX;
        this.screenY = screenY;

        screenRatioX = 2560f / screenX;
        screenRatioY = 1440f / screenY;

        background1 = new planet13_background(screenX, screenY, getResources());
        background2 = new planet13_background(screenX, screenY, getResources());

        planet13_Game_start = new planet13_game_start(this, screenY, getResources());

        planet13_bullets = new ArrayList<>();

        background2.x = screenX;

        paint = new Paint();
        paint.setTextSize(128);
        paint.setColor(Color.WHITE);

        planet13_enemies = new planet13_enemy[4];

        for(int i = 0; i < 4; i++){

            planet13_enemy planet13_Enemy = new planet13_enemy(getResources());
            planet13_enemies[i] = planet13_Enemy;
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

        if (background1.x + background1.planet13_Background.getWidth() < 0) {
            background1.x = screenX;
        }

        if (background2.x + background2.planet13_Background.getWidth() < 0) {
            background2.x = screenX;
        }

        if (planet13_Game_start.isGoingUp)
            planet13_Game_start.y -= 30 * screenRatioY;
        else
            planet13_Game_start.y += 30 * screenRatioY;

        if (planet13_Game_start.y < 0)
            planet13_Game_start.y = 0;

        if (planet13_Game_start.y > screenY - planet13_Game_start.height)
            planet13_Game_start.y = screenY - planet13_Game_start.height;


        List<planet13_bullet> planet13_trash = new ArrayList<>();

        for (planet13_bullet planet13_Bullet : planet13_bullets){

            if (planet13_Bullet.x > screenX)
                planet13_trash.add(planet13_Bullet);

            planet13_Bullet.x += 50 * screenRatioX;

            for (planet13_enemy planet13_enemy : planet13_enemies){

                if(Rect.intersects(planet13_enemy.getCollisionShape(), planet13_Bullet.getCollisionShape())){

                    kill++;
                    planet13_enemy.x = -500;
                    planet13_Bullet.x = screenX + 500;
                    planet13_enemy.wasShot = true;
                }
                if(kill == 50){
                    stageFinished = true;
                }

            }
        }

        for (planet13_bullet planet13_Bullet : planet13_trash)
            planet13_bullets.remove(planet13_Bullet);

        for (planet13_enemy planet13_enemy : planet13_enemies) {

            planet13_enemy.x -= planet13_enemy.speed;

            if (planet13_enemy.x + planet13_enemy.width < 0) {

                if (!planet13_enemy.wasShot) {
                    life--;
                    return;
                }


                int bound = (int) (30 * screenRatioX);
                planet13_enemy.speed = random.nextInt(bound);

                if (planet13_enemy.speed < 10 * screenRatioX)
                    planet13_enemy.speed = (int) (10 * screenRatioX);

                planet13_enemy.x = screenX;
                planet13_enemy.y = random.nextInt(screenY - planet13_enemy.height);

                planet13_enemy.wasShot = false;


            }
            if (Rect.intersects(planet13_Game_start.getCollisionShape(), planet13_enemy.getCollisionShape())) {
                life--;
                planet13_enemy.x = -500;
                planet13_enemy.wasShot = true
                ;

            }
            if (life == 0 ){
                isGameOver = true;
                return;
            }
        }

    }






    private void draw () {

        if (getHolder().getSurface().isValid()){

            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.planet13_Background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.planet13_Background, background2.x, background2.y, paint);

            for (planet13_enemy planet13_enemy : planet13_enemies)
                canvas.drawBitmap(planet13_enemy.getplanet13_enemy(), planet13_enemy.x, planet13_enemy.y, paint);

            canvas.drawText(kill + "", screenX / 2f, 164,  paint);

            if (isGameOver){
                isPlaying = false;
                canvas.drawBitmap(planet13_Game_start.getDead(), planet13_Game_start.x, planet13_Game_start.y, paint);
                saveIfDone();
                waitBeforeExiting();
                getHolder().unlockCanvasAndPost(canvas);
                return ;
            }

            if (stageFinished){
                isPlaying = false;
                saveIfDone();
                waitBeforeExiting();
            }


            canvas.drawBitmap(planet13_Game_start.getPlanet1_game_start(), planet13_Game_start.x,planet13_Game_start.y, paint);

            for(planet13_bullet planet13Bullet : planet13_bullets)
                canvas.drawBitmap(planet13Bullet.planet13_Bullet, planet13Bullet.x, planet13Bullet.y, paint);

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
            Thread.sleep(1000);
            activity.startActivity(new Intent(activity, planet13_game.class));
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
                    planet13_Game_start.isGoingUp = true;
                }
                break;
            case  MotionEvent.ACTION_UP:
                planet13_Game_start.isGoingUp= false;

                if (event.getX() > screenX / 2)
                    planet13_Game_start.toShoot++;
                break;

        }


        return true;
    }

    public void newBullet() {

        planet13_bullet planet13_Bullet = new planet13_bullet(getResources());
        planet13_Bullet.x = planet13_Game_start.x + planet13_Game_start.width;
        planet13_Bullet.y = planet13_Game_start.y + (planet13_Game_start.height/10) ;

        planet13_bullets.add(planet13_Bullet);

    }
}

